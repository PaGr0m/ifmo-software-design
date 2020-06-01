package parser;

import commands.CommandException;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Парсер строки разбивающий символы на лексемы
 */
public class Parser {
    private int position;
    private StringBuilder word;

    public Parser() {
        this.position = 0;
        this.word = new StringBuilder();
    }

    /**
     * Парсер строки
     *
     * @param input входящая строка
     * @return список созданных лексем
     */
    public List<Lexeme> parse(@NotNull String input) {
        List<Lexeme> lexemes = new ArrayList<>();

        try {
            while (position < input.length()) {
                switch (input.charAt(position)) {
                    case ParserSymbols.DOUBLE_QUOTE: {
                        lexemes.add(parseWordInQuote(input, ParserSymbols.DOUBLE_QUOTE));
                        skipSpaces(input).ifPresent(lexemes::add);
                        break;
                    }
                    case ParserSymbols.SINGLE_QUOTE: {
                        lexemes.add(parseWordInQuote(input, ParserSymbols.SINGLE_QUOTE));
                        skipSpaces(input).ifPresent(lexemes::add);
                        break;
                    }
                    case ParserSymbols.SUBSTITUTE: {
                        lexemes.add(parseSubstitute(input));
                        skipSpaces(input).ifPresent(lexemes::add);
                        break;
                    }
                    case ParserSymbols.ASSIGNMENT: {
                        lexemes.add(parseAssignment(input));
                        break;
                    }
                    case ParserSymbols.SPACE: {
                        parseWord().ifPresent(lexemes::add);
                        skipSpaces(input).ifPresent(lexemes::add);
                        break;
                    }
                    default: {
                        word.append(input.charAt(position));
                        position++;
                        break;
                    }
                }
            }

            parseWord().ifPresent(lexemes::add);
            position = 0;
        } catch (IndexOutOfBoundsException e) {
            throw new CommandException("Wrong input");
        }

        return lexemes;
    }

    /**
     * Пропуск все пробельных символов
     *
     * @param input входящая строка
     * @return лексема соответствующая пробельному символу или ничего
     */
    private @NotNull Optional<Lexeme> skipSpaces(@NotNull String input) {
        Lexeme lexeme = null;
        if (position < input.length() && input.charAt(position) == ParserSymbols.SPACE) {
            lexeme = Lexeme.builder()
                           .word(" ")
                           .type(LexemeType.SPACE)
                           .build();
        }

        while (position < input.length() && input.charAt(position) == ParserSymbols.SPACE) {
            position++;
        }

        return Optional.ofNullable(lexeme);
    }

    /**
     * Парсинг строки в двойных кавычках
     *
     * @param input     входящая строка
     * @param quoteType тип кавычки ('', "")
     * @return лексема соответствующая двойным кавычкам
     */
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

        return Lexeme.builder()
                     .word(wordInQuote.toString())
                     .type(quoteType == ParserSymbols.DOUBLE_QUOTE ? LexemeType.WORD_IN_DOUBLE_QUOTE
                                                                   : LexemeType.WORD_IN_SINGLE_QUOTE)
                     .build();
    }

    /**
     * Парсинг строки с найденным символом подстановки ($)
     *
     * @param input входящая строка
     * @return лексема соответствующая подстановке
     */
    private @NotNull Lexeme parseSubstitute(@NotNull String input) {
        StringBuilder variable = new StringBuilder();
        variable.append(ParserSymbols.SUBSTITUTE);
        position++;
        while (position < input.length() &&
               input.charAt(position) != ParserSymbols.SPACE &&
               input.charAt(position) != ParserSymbols.SUBSTITUTE) {
            variable.append(input.charAt(position));
            position++;
        }

//        skipSpaces(input);
        return Lexeme.builder()
                     .word(variable.toString())
                     .type(LexemeType.WORD_VARIABLE)
                     .build();
    }

    /**
     * Парсинг строки с присвоением (=)
     *
     * @param input входящая строка
     * @return лексема соответствующая присвоению
     */
    private @NotNull Lexeme parseAssignment(@NotNull String input) {
        while (position < input.length() && input.charAt(position) != ParserSymbols.SPACE) {
            word.append(input.charAt(position));
            position++;
        }

        Lexeme lexeme = Lexeme.builder()
                              .word(word.toString())
                              .type(LexemeType.WORD_ASSIGMENT)
                              .build();
        word = new StringBuilder();
        return lexeme;
    }

    /**
     * Парсинг обычного слова
     *
     * @return лексема соответствующая слову или ничего, если слова не было
     */
    private @NotNull Optional<Lexeme> parseWord() {
        Lexeme lexeme = null;
        if (word.length() > 0) {
            lexeme = Lexeme.builder()
                           .word(word.toString())
                           .type(LexemeType.WORD)
                           .build();
            word = new StringBuilder();
        }

        return Optional.ofNullable(lexeme);
    }
}
