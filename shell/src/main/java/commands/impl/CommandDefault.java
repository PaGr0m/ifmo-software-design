package commands.impl;

import commands.Command;
import commands.CommandException;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Команда которой нет в данном shell, но можно вызвать из bash
 */
public class CommandDefault implements Command {
    private final String commandName;

    public CommandDefault(String commandName) {
        this.commandName = commandName;
    }

    /**
     * Запуск команды при помощи <code> Process </code>
     *
     * @param arguments аргументы для команды
     * @return возвращаемое значение команды
     */
    @Override
    public String run(@NotNull List<String> arguments) {
        String line;
        StringBuilder result = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec(commandName + " " + joinArguments(arguments));
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

            while ((line = input.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (IOException e) {
            return "Command not found: " + commandName;
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new CommandException("Wrong input");
        }

        return result.toString();
    }
}
