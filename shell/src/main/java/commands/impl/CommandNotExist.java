package commands.impl;

import commands.Command;

import java.util.List;

public class CommandNotExist implements Command {

    @Override
    public String run(List<String> arguments) {
        return "Command not found";
    }
}
