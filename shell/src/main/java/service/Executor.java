package service;

import commands.CommandEntity;
import commands.CommandExecutor;
import org.jetbrains.annotations.NotNull;
import parser.Lexeme;
import parser.LexemType;
import parser.Parser;
import parser.Substitutor;

import java.util.List;
import java.util.stream.Collectors;

public class Executor {
    private final Parser parser = new Parser();
    private final Environment environment = new Environment();
    private final Substitutor substitutor = new Substitutor();
    private final CommandExecutor commandExecutor = new CommandExecutor();

    public String execute(@NotNull String input) {
//        if (input.contains("|")) {
//            List<CommandEntity> commands = new ArrayList<>();
//
//            String[] cmds = input.split("\\|");
//            for (String cmd : cmds) {
//                List<Lexem> lexems = parser.parseLexem(cmd);
//                substitutor.substitution(lexems);
//
//                commands.add(
//                        CommandEntity.builder()
//                                     .name(lexems.get(0).getWord())
//                                     .arguments(lexems.stream()
//                                                      .filter(lexem -> lexems.indexOf(lexem) != 0)
//                                                      .map(Lexem::getWord)
//                                                      .collect(Collectors.toList()))
//                                     .build());
//            }
//
//            String output = commandExecutor.execute(commands.get(0));
//            commands.remove(0);
//            for (CommandEntity command : commands) {
//                command.setArguments(Collections.singletonList(output));
//                output = commandExecutor.execute(command);
//            }
//
//            return output;
//        }

        List<Lexeme> lexemes = parser.parse(input);
        substitutor.substitution(lexemes);

        for (Lexeme lexeme : lexemes) {
            if (lexeme.getType() == LexemType.WORD_ASSIGMENT) {
                String[] attrAndValue = input.split("=");
                environment.putVariable(attrAndValue[0], attrAndValue[1]);
                return attrAndValue[0] + " = " + attrAndValue[1];
            }

            CommandEntity command =
                    CommandEntity.builder()
                                 .name(lexemes.get(0).getWord())
                                 .arguments(lexemes.stream()
                                                  .filter(l -> lexemes.indexOf(l) != 0)
                                                  .map(Lexeme::getWord)
                                                  .collect(Collectors.toList()))
                                 .build();

            return commandExecutor.execute(command);
        }

        return "";
    }
}
