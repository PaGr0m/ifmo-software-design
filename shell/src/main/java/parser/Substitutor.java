package parser;

import org.jetbrains.annotations.NotNull;
import service.Environment;

import java.util.List;

/**
 * Подстановщик, который подставляет переменные на все места символа ($)
 */
public class Substitutor {
    private final Environment environment;

    public Substitutor(Environment environment) {
        this.environment = environment;
    }

    /**
     * Поиск подстановочного символа, для замены на переменной на значение
     *
     * @param lexemes список лексем
     */
    public void substitution(@NotNull List<Lexeme> lexemes) {
        for (Lexeme lexeme : lexemes) {

            // Поиск символа ($) в лексеме слова
            if (lexeme.type == LexemeType.WORD_VARIABLE) {
                String variable = lexeme.word.substring(findSubstitutionSymbol(lexeme) + 1);
                lexeme.word = lexeme.word.replace(ParserSymbols.SUBSTITUTE + variable,
                                                  environment.getVariable(variable));
                lexeme.type = LexemeType.WORD;
            }

            // Поиск символа ($) в лексеме двойных кавычек
            if (lexeme.type == LexemeType.WORD_IN_DOUBLE_QUOTE) {
                if (lexeme.word.contains(String.valueOf(ParserSymbols.SUBSTITUTE))) {
                    String partLeft = lexeme.word.substring(0, findSubstitutionSymbol(lexeme));
                    int idx = lexeme.word.indexOf(ParserSymbols.SUBSTITUTE);
                    while (lexeme.word.charAt(idx) != ParserSymbols.SPACE &&
                           lexeme.word.charAt(idx) != ParserSymbols.DOUBLE_QUOTE &&
                           lexeme.word.charAt(idx) != ParserSymbols.SINGLE_QUOTE) {
                        idx++;
                    }

                    String centralPart = lexeme.word.substring(findSubstitutionSymbol(lexeme) + 1, idx);
                    lexeme.word = partLeft + environment.getVariable(centralPart) + lexeme.word.substring(idx);
                    lexeme.type = LexemeType.WORD;
                }
            }
        }
    }

    /**
     * Поиск индекса символа подстановки ($)
     *
     * @param lexeme лексема
     * @return индекс символа
     */
    private int findSubstitutionSymbol(@NotNull Lexeme lexeme) {
        return lexeme.word.indexOf(ParserSymbols.SUBSTITUTE);
    }
}
