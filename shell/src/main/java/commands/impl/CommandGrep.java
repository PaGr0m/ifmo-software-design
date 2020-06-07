package commands.impl;

import commands.Command;
import commands.CommandException;
import org.apache.commons.cli.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        String searchWord = argList.remove(0);

        int counter = 0;
        int lineCount = getCountLinesByFlag(commandLine);

        boolean isAdd = false;
        CommandCat commandCat = new CommandCat();
        List<String> result = new ArrayList<>();
        for (String originalRow : commandCat.run(argList).split("\n")) {
            String row = originalRow.replaceAll("['\"]", "");

            if (counter != 0) {
                result.add(originalRow);
                counter--;
            }

            if (commandLine.hasOption("i") && commandLine.hasOption("w")) {
                if (conditionCaseAndWordEquals(row, searchWord)) {
                    isAdd = true;
                }
            } else if (commandLine.hasOption("i")) {
                if (conditionCase(row, searchWord)) {
                    isAdd = true;
                }
            } else if (commandLine.hasOption("w")) {
                if (conditionWordEquals(row, searchWord)) {
                    isAdd = true;
                }
            } else {
                if (conditionDefault(row, searchWord)) {
                    isAdd = true;
                }
            }

            if (isAdd) {
                result.add(originalRow);
                counter = lineCount;
                isAdd = false;
            }
        }

        return result.stream().distinct().collect(Collectors.joining("\n"));
    }

    /**
     * Условие того, что используется флаг нечувствительности к регистру (-i)
     * Строка и слово приводятся с нижнему регистру и проверяется вхождение слова в строку
     *
     * @param row        строка
     * @param searchWord слово, которое необходимо найти
     * @return true - если условие выполняется, false - иначе
     */
    private boolean conditionCase(@NotNull String row, String searchWord) {
        return Arrays.stream(row.toLowerCase().split(" "))
                     .anyMatch(str -> str.toLowerCase().contains(searchWord.toLowerCase()));
    }

    /**
     * Условие того, что используется флаг нечувствительности к регистру (-i)
     * Строка и слово приводятся с нижнему регистру и проверяется вхождение слова в строку
     *
     * @param row        строка
     * @param searchWord слово, которое необходимо найти
     * @return true - если условие выполняется, false - иначе
     */
    private boolean conditionWordEquals(@NotNull String row, String searchWord) {
        return Arrays.asList(row.split(" ")).contains(searchWord);
    }

    /**
     * Условие того, что используется флаг поиска слова целиком (-w)
     * В строке проверяется есть ли в ней слово, которое точное сопоставимо к искомым
     *
     * @param row        строка
     * @param searchWord слово, которое необходимо найти
     * @return true - если условие выполняется, false - иначе
     */
    private boolean conditionCaseAndWordEquals(@NotNull String row, @NotNull String searchWord) {
        return Arrays.asList(row.toLowerCase().split(" ")).contains(searchWord.toLowerCase());
    }

    /**
     * Условие того без флагов
     *
     * @param row        строка
     * @param searchWord слово, которое необходимо найти
     * @return true - если условие выполняется, false - иначе
     */
    private boolean conditionDefault(@NotNull String row, String searchWord) {
        return Arrays.stream(row.split(" ")).anyMatch(str -> str.contains(searchWord));
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
}
