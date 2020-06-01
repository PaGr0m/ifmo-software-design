package commands;

import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

/**
 * Класс, определяющий класс команды и передающий аргументы в него
 */
public class CommandExecutor {
    public String execute(@NotNull CommandEntity commandEntity) {
        Command command = commandEntity.getCommand();
        return command.run(commandEntity.getArguments()
                                        .stream()
                                        .map(String::trim)
                                        .collect(Collectors.toList()));
    }
}
