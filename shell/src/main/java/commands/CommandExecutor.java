package commands;

import org.jetbrains.annotations.NotNull;

public class CommandExecutor {
    public String execute(@NotNull CommandEntity commandEntity) {
        Command command = commandEntity.getCommand();
        return command.run(commandEntity.getArguments());
    }
}
