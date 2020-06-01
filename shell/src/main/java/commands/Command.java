package commands;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Интерфейс для команды
 */
public interface Command {
    String run(List<String> arguments);

    default String joinArguments(@NotNull List<String> arguments) {
        return arguments.stream()
                        .filter(str -> !str.equals(""))
                        .map(String::trim)
                        .collect(Collectors.joining(" "));
    }
}
