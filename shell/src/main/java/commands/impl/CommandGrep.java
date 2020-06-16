package commands.impl;

import commands.Command;
import commands.CommandException;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Команда соответствующая bash grep
 */
public class CommandGrep implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return результат выполнения команды
     */
    @Override
    public String run(@NotNull List<String> arguments) {
        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine;
        try {
            commandLine = parser.parse(getOptions(), arguments.stream()
                                                              .filter(str -> !str.equals(""))
                                                              .map(String::trim)
                                                              .toArray(String[]::new));
        } catch (ParseException e) {
            throw new CommandException("Wrong input");
        }

        return executeGrep(commandLine);
    }

    /**
     * Логика команды grep
     *
     * @param commandLine объект командной строки
     * @return результат выполнения команды
     */
    private String executeGrep(@NotNull CommandLine commandLine) {
        List<String> argList = commandLine.getArgList();
        if (argList.isEmpty()) {
            throw new CommandException("Command without arguments");
        }

        Pattern pattern = Pattern.compile(argList.remove(0),
                                          commandLine.hasOption("i") ? Pattern.CASE_INSENSITIVE : 0);

        Predicate<CharSequence> condition = commandLine.hasOption("w") ? wordEquals(pattern)
                                                                       : notWordEquals(pattern);

        int counter = 0;
        int lineCount = getCountLinesByFlag(commandLine);

        List<String> result = new ArrayList<>();
        for (String originalRow : new CommandCat().run(argList).split("\n")) {
            String row = originalRow.replaceAll("['\"]", "");

            if (counter != 0) {
                result.add(originalRow);
                counter--;
            }

            if (isCorrectRow(row, condition)) {
                result.add(originalRow);
                counter = lineCount;
            }
        }

        return result.stream().distinct().collect(Collectors.joining("\n"));
    }

    /**
     * Функция, которая проверяет совпадение строки с паттерном.
     *
     * @param row       проверяемая строка
     * @param condition условие по которому происходит проверка
     * @return если условие выполняется, false - иначе
     */
    private boolean isCorrectRow(@NotNull String row, @NotNull Predicate<CharSequence> condition) {
        return Arrays.stream(row.split("\\s+"))
                     .map(condition::test)
                     .reduce((acc, elem) -> acc |= elem)
                     .orElse(false);
    }

    /**
     * Считывание количества строк, которые требуется распечатать при флаге А
     *
     * @param commandLine объект командной строки
     * @return значение флага, либо 0, если его нет
     */
    private int getCountLinesByFlag(@NotNull CommandLine commandLine) {
        if (commandLine.hasOption("A")) {
            try {
                return Integer.parseInt(commandLine.getOptionValue("A"));
            } catch (NumberFormatException e) {
                throw new CommandException("Wrong input with flag A");
            }
        }

        return 0;
    }

    /**
     * Опции для команды grep
     *
     * @return объект опции
     */
    private @NotNull Options getOptions() {
        Options options = new Options();

        // -i (нечувствительность к регистру),
        options.addOption("i", "i", false, "Case insensitive.");

        // -w (поиск только слов целиком),
        options.addOption("w", "w", false, "Search only whole words.");

        // -A n (распечатать n строк после строки с совпадением);
        options.addOption("A", "A", true, "print n lines after line matching");

        return options;
    }

    /**
     * Предикат проверки не полного вхождения слова в строке
     *
     * @param pattern паттерн проверки со строкой
     * @return если условие выполняется, false - иначе
     */
    @Contract(pure = true)
    private @NotNull Predicate<CharSequence> notWordEquals(Pattern pattern) {
        return str -> pattern.matcher(str).find();
    }

    /**
     * Предикат проверки полного вхождения слова в строке
     *
     * @param pattern паттерн проверки со строкой
     * @return если условие выполняется, false - иначе
     */
    @Contract(pure = true)
    private @NotNull Predicate<CharSequence> wordEquals(Pattern pattern) {
        return str -> pattern.matcher(str).matches();
    }
}
