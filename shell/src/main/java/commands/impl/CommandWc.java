package commands.impl;

import commands.Command;
import commands.CommandException;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Команда соответствующая bash wc
 */
public class CommandWc implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return возвращаемое значение команды
     */
    @Override
    public String run(@NotNull List<String> arguments) {
        List<FileStatistics> statistics = new ArrayList<>();
        for (String argument : joinArguments(arguments).split(" ")) {
            File file = new File(argument);

            if (file.isDirectory()) {
                return "This path is directory!";
            }

            if (!file.isFile()) {
                return countStatistics(new Scanner(joinArguments(arguments))).toString();
            }

            try (Scanner scanner = new Scanner(file)) {
                FileStatistics fileStatistics = countStatistics(scanner);
                fileStatistics.setDescription(file.getPath());
                statistics.add(fileStatistics);
            } catch (FileNotFoundException e) {
                throw new CommandException("No such file");
            }
        }

        addTotalStatistic(statistics);

        return statistics.stream().map(FileStatistics::toString).collect(Collectors.joining("\n"));
    }

    /**
     * Добавление общей статистики, если файлов больше чем 1
     *
     * @param statistics список статистик по файлам
     */
    private void addTotalStatistic(@NotNull List<FileStatistics> statistics) {
        if (statistics.size() > 1) {
            statistics.add(new FileStatistics(
                    statistics.stream().mapToInt(FileStatistics::getLines).sum(),
                    statistics.stream().mapToInt(FileStatistics::getWords).sum(),
                    statistics.stream().mapToInt(FileStatistics::getBytes).sum(),
                    "total"));
        }
    }

    /**
     * Подсчет статистики
     *
     * @param scanner сканер файла или строки
     * @return статистика файла или строки
     */
    private @NotNull FileStatistics countStatistics(@NotNull Scanner scanner) {
        FileStatistics fileStatistics = new FileStatistics();

        while (scanner.hasNextLine()) {
            List<String> line = Arrays.asList(scanner.nextLine().split(" "));
            fileStatistics.bytes += line.stream().mapToInt(String::length).sum();
            fileStatistics.words += line.size();
            fileStatistics.lines++;
        }

        return fileStatistics;
    }

    /**
     * Класс статистики в котором хранится количество:
     * <li> строк </li>
     * <li> слов </li>
     * <li> байт </li>
     * <li> название файла (опционально) </li>
     */
    @Getter
    @Setter
    private static class FileStatistics {
        private int lines;
        private int words;
        private int bytes;
        private String description;

        public FileStatistics() {
            this.lines = 0;
            this.words = 0;
            this.bytes = 0;
            this.description = "";
        }

        public FileStatistics(int lines, int words, int bytes, String description) {
            this.lines = lines;
            this.words = words;
            this.bytes = bytes;
            this.description = description;
        }

        @Override
        public String toString() {
            return lines + " " +
                   words + " " +
                   bytes + " " +
                   description;
        }
    }
}
