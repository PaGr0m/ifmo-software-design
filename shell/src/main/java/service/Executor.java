package service;

import commands.CommandEntity;
import commands.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import parser.LexemType;
import parser.Lexeme;
import parser.Parser;
import parser.Substitutor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Executor {
    private final Parser parser = new Parser();
    private final Environment environment = new Environment();
    private final Substitutor substitutor = new Substitutor(environment);
    private final CommandExecutor commandExecutor = new CommandExecutor();

    public String execute(@NotNull String input) {
        if (input.contains("|")) {
            return executePipesCommand(createCommandList(input));
        }

        List<Lexeme> lexemes = parser.parse(input);
        substitutor.substitution(lexemes);

        if (lexemes.stream().anyMatch(lexeme -> lexeme.getType() == LexemType.WORD_ASSIGMENT)) {
            assigmentList(lexemes);
            return "";
        }

        return commandExecutor.execute(createCommand(lexemes));
    }

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

    private void assigmentList(@NotNull List<Lexeme> lexemes) {
        lexemes.stream()
               .filter(lexeme -> lexeme.getType() == LexemType.WORD_ASSIGMENT)
               .forEach(lexeme -> {
                   String[] attrAndValue = lexeme.getWord().split("=");
                   environment.putVariable(attrAndValue[0], attrAndValue[1]);
               });
    }

    private CommandEntity createCommand(@NotNull List<Lexeme> lexemes) {
        boolean flag = true;
        StringBuilder stringBuilder = new StringBuilder();
        List<String> args = new ArrayList<>();
        for (Lexeme lexeme : lexemes) {
            if (lexeme.getType() == LexemType.SPACE) {
                flag = false;
            }

            if (lexeme.getType() != LexemType.SPACE && flag) {
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
