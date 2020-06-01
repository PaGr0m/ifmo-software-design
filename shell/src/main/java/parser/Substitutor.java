package parser;

import org.jetbrains.annotations.NotNull;
import service.Environment;

import java.util.List;

//TODO: разнести логику
public class Substitutor {
    private final Environment environment;

    public Substitutor(Environment environment) {
        this.environment = environment;
    }

    public void substitution(@NotNull List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {
            if (lexeme.type == LexemType.WORD_VARIABLE) {
                String variable = lexeme.word.substring(lexeme.word.indexOf(ParserSymbols.SUBSTITUTE) + 1);
                lexeme.word = lexeme.word.replace(ParserSymbols.SUBSTITUTE + variable,
                                                  environment.getVariable(variable));
                lexeme.type = LexemType.WORD;
            }

            if (lexeme.type == LexemType.WORD_IN_DOUBLE_QUOTE) {
                if (lexeme.word.contains(String.valueOf(ParserSymbols.SUBSTITUTE))) {
                    String partLeft = lexeme.word.substring(0, lexeme.word.indexOf(ParserSymbols.SUBSTITUTE));
                    int idx = lexeme.word.indexOf(ParserSymbols.SUBSTITUTE);
                    while (lexeme.word.charAt(idx) != ParserSymbols.SPACE &&
                           lexeme.word.charAt(idx) != ParserSymbols.DOUBLE_QUOTE &&
                           lexeme.word.charAt(idx) != ParserSymbols.SINGLE_QUOTE) {
                        idx++;
                    }

                    String centralPart = lexeme.word.substring(lexeme.word.indexOf(ParserSymbols.SUBSTITUTE) + 1,
                                                               idx);
                    lexeme.word = partLeft + environment.getVariable(centralPart) + lexeme.word.substring(idx);
                    lexeme.type = LexemType.WORD;
                }
            }
        }
    }
}
