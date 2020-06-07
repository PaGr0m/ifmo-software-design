package commands.impl;

import commands.Command;

import java.util.List;

/**
 * Команда соответствующая bash pwd
 */
public class CommandPwd implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return результат выполнения команды
     */
    @Override
    public String run(List<String> arguments) {
        return System.getProperty("user.dir");
    }
}
