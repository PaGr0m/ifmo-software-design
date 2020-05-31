package parser;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Parser {
    private int position;
    private StringBuilder word;

    public Parser() {
        this.position = 0;
        this.word = new StringBuilder();
    }

    public List<Lexeme> parse(@NotNull String input) {
        List<Lexeme> lexemes = new ArrayList<>();

        while (position < input.length()) {
            switch (input.charAt(position)) {
                case ParserSymbols.DOUBLE_QUOTE: {
                    lexemes.add(parseWordInQuote(input, ParserSymbols.DOUBLE_QUOTE));
                    break;
                }
                case ParserSymbols.SINGLE_QUOTE: {
                    lexemes.add(parseWordInQuote(input, ParserSymbols.SINGLE_QUOTE));
                    break;
                }
                case ParserSymbols.SUBSTITUTE: {
                    lexemes.add(parseSubstitute(input));
                    break;
                }
                case ParserSymbols.ASSIGNMENT: {
                    lexemes.add(parseAssignment(input));
                    break;
                }
                case ParserSymbols.SPACE: {
                    parseWord(input).ifPresent(lexemes::add);
                    break;
                }
                default: {
                    word.append(input.charAt(position));
                    position++;
                    break;
                }
            }
        }

        parseWord(input).ifPresent(lexemes::add);

        return lexemes;
    }

    private void skipSpaces(@NotNull String input) {
        while (position < input.length() && input.charAt(position) == ParserSymbols.SPACE) {
            position++;
        }
    }

    private @NotNull Lexeme parseWordInQuote(@NotNull String input, char quoteType) {
        StringBuilder wordInQuote = new StringBuilder();
        wordInQuote.append(quoteType);
        position++;
        while (input.charAt(position) != quoteType) {
            wordInQuote.append(input.charAt(position));
            position++;
        }
        position++;
        wordInQuote.append(quoteType);

        skipSpaces(input);
        return Lexeme.builder()
                     .word(wordInQuote.toString())
                     .type(quoteType == ParserSymbols.DOUBLE_QUOTE ? LexemType.WORD_IN_DOUBLE_QUOTE
                                                                   : LexemType.WORD_IN_SINGLE_QUOTE)
                     .build();
    }

    private @NotNull Lexeme parseSubstitute(@NotNull String input) {
        StringBuilder variable = new StringBuilder();
        while (position < input.length() && input.charAt(position) != ParserSymbols.SPACE) {
            variable.append(input.charAt(position));
            position++;
        }
        position++;

        skipSpaces(input);
        return Lexeme.builder()
                     .word(variable.toString())
                     .type(LexemType.WORD_VARIABLE)
                     .build();
    }

    private @NotNull Lexeme parseAssignment(@NotNull String input) {
        while (position < input.length() && input.charAt(position) != ParserSymbols.SPACE) {
            word.append(input.charAt(position));
            position++;
        }

        Lexeme lexeme = Lexeme.builder()
                              .word(word.toString())
                              .type(LexemType.WORD_ASSIGMENT)
                              .build();
        word = new StringBuilder();
        return lexeme;
    }

    private @NotNull Optional<Lexeme> parseWord(@NotNull String input) {
        Lexeme lexeme = null;
        if (word.length() > 0) {
            lexeme = Lexeme.builder()
                           .word(word.toString())
                           .type(LexemType.WORD)
                           .build();
            word = new StringBuilder();
        }
        skipSpaces(input);

        return Optional.ofNullable(lexeme);
    }
}
