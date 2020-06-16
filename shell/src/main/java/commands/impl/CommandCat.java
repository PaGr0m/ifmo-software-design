package commands.impl;

import commands.Command;
import commands.CommandException;
import org.jetbrains.annotations.NotNull;
import service.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Команда соответствующая bash cat
 */
public class CommandCat implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return возвращаемое значение команды
     */
    @Override
    public String run(@NotNull List<String> arguments) {
        String args = joinArguments(arguments);

        Path path;
        if (Paths.get(Environment.getCurrentPath(), args).toFile().exists()) {
            path = Paths.get(Environment.getCurrentPath(), args);
        } else if (Paths.get(args).toFile().exists()) {
            path = Paths.get(args);
        } else {
            return args;
        }

        if (path.toFile().isDirectory()) {
            throw new CommandException("Wrong input");
        }

        try (Stream<String> stream = Files.lines(path)) {
            return stream.collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new CommandException("File is not exist!");
        }
    }
}
