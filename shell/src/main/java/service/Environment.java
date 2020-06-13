package service;

import commands.Command;
import commands.CommandConstants;
import commands.impl.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Класс окружения, который хранит все все доступные
 * команды и переменные окружения
 * </p>
 */
public class Environment {
    private static final String currentPath = System.getProperty("user.dir");
    private static final Map<String, Command> commands = new HashMap<>();
    private static final Map<String, String> variables = new HashMap<>();

    // Список доступных команд
    static {
        commands.put(CommandConstants.COMMAND_ECHO, new CommandEcho());
        commands.put(CommandConstants.COMMAND_EXIT, new CommandExit());
        commands.put(CommandConstants.COMMAND_CAT, new CommandCat());
        commands.put(CommandConstants.COMMAND_PWD, new CommandPwd());
        commands.put(CommandConstants.COMMAND_WC, new CommandWc());
    }

    // Список переменных окружения
    static {
        variables.put("PWD", "/home/pagrom");
        variables.put("HOME", "/home/pagrom/");
        variables.put("PATH", "/home/pagrom/.local/bin");
        variables.put("USER", "pagrom");
        variables.put("SHELL", "/usr/bin/zsh");
        variables.put("LOGNAME", "pagrom");
    }

    public Command getCommand(String commandName) {
        return commands.getOrDefault(commandName, new CommandDefault(commandName));
    }

    public void putVariable(String argumentName, String argumentValue) {
        variables.put(argumentName, argumentValue);
    }

    public String getVariable(String variableName) {
        return variables.getOrDefault(variableName, "Variable not exist!");
    }

    public static String getCurrentPath() {
        return currentPath;
    }
}
