package commands.impl;

import commands.Command;

import java.util.List;

/**
 * Команда соответствующая bash exit
 */
public class CommandExit implements Command {

    /**
     * Запуск команды
     *
     * @param arguments аргументы для команды
     * @return результат выполнения команды
     */
    @Override
    public String run(List<String> arguments) {
        System.exit(0);
        return null;
    }
}
