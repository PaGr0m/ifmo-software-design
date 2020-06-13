package service;

import commands.CommandEntity;
import commands.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import parser.Lexeme;
import parser.LexemeType;
import parser.Parser;
import parser.Substitutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс, который выполняет операции из командной строки
 */
public class Executor {
    private final Parser parser = new Parser();
    private final Environment environment = new Environment();
    private final Substitutor substitutor = new Substitutor(environment);
    private final CommandExecutor commandExecutor = new CommandExecutor();

    /**
     * Запуск команды из консоли
     *
     * @param input входящая строка
     * @return результат выполнения команды
     */
    public String execute(@NotNull String input) {
        if (input.contains("|")) {
            return executePipesCommand(createCommandList(input));
        }

        List<Lexeme> lexemes = parser.parse(input);
        substitutor.substitution(lexemes);

        if (lexemes.stream().anyMatch(lexeme -> lexeme.getType() == LexemeType.WORD_ASSIGMENT)) {
            return assigmentList(lexemes);
        }

        return commandExecutor.execute(createCommand(lexemes));
    }

    /**
     * Создание списка команд из пайпов (|)
     *
     * @param input входящая строка
     * @return список разделенных команд
     */
    private @NotNull List<CommandEntity> createCommandList(@NotNull String input) {
        List<CommandEntity> commandsList = new ArrayList<>();

        String[] commands = input.split("\\|");
        for (String cmd : commands) {
            List<Lexeme> lexemes = parser.parse(cmd.trim());
            substitutor.substitution(lexemes);
            commandsList.add(createCommand(lexemes));
        }

        return commandsList;
    }

    /**
     * Поиск всех присваивающих лексем и добавление в окружение
     *
     * @param lexemes список лексем
     * @return строковое представление списка присваиваний
     */
    private String assigmentList(@NotNull List<Lexeme> lexemes) {
        return lexemes.stream()
                      .filter(lexeme -> lexeme.getType() == LexemeType.WORD_ASSIGMENT)
                      .map(lexeme -> lexeme.getWord().split("="))
                      .peek(varAndValue -> environment.putVariable(varAndValue[0], varAndValue[1]))
                      .map(varAndValue -> varAndValue[0] + " = " + varAndValue[1])
                      .collect(Collectors.joining("\n"));
    }

    /**
     * Создание сущности команды
     *
     * @param lexemes список лексем
     * @return сущность команды
     */
    private CommandEntity createCommand(@NotNull List<Lexeme> lexemes) {
        boolean flag = true;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> args = new ArrayList<>();
        for (Lexeme lexeme : lexemes) {
            if (lexeme.getType() == LexemeType.SPACE) {
                flag = false;
            }

            if (lexeme.getType() != LexemeType.SPACE && flag) {
                stringBuilder.append(lexeme.getWord());
            } else {
                args.add(lexeme.getWord());
            }
        }

        return CommandEntity.builder()
                            .command(environment.getCommand(stringBuilder.toString()))
                            .arguments(args)
                            .build();
    }

    /**
     * Поочередное выполнение команд и пайпа (|)
     * Результат выполнения первой команды передается как аргументы для второй и так далее
     *
     * @param commandsList список команд
     * @return результат выполнения всех команд
     */
    private String executePipesCommand(@NotNull List<CommandEntity> commandsList) {
        String output = commandExecutor.execute(commandsList.get(0));

        commandsList.remove(0);
        for (CommandEntity command : commandsList) {
            command.setArguments(Collections.singletonList(output));
            output = commandExecutor.execute(command);
        }

        return output;
    }
}
