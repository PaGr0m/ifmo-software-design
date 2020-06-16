package commands.impl;

import commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Команда соответствующая bash echo
 */
public class CommandEcho implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return результат выполнения команды
     */
    @Override
    public String run(@NotNull List<String> arguments) {
        return arguments.stream()
                        .map(String::trim)
                        .filter(str -> !str.equals(""))
                        .map(arg -> {
                            if ((arg.startsWith("\"") && arg.endsWith("\"")) ||
                                (arg.startsWith("'") && arg.endsWith("'"))) {
                                arg = arg.substring(1, arg.length() - 1);
                            }
                            return arg;
                        })
                        .collect(Collectors.joining(" "));
    }
}
