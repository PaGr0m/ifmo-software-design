package commands.impl;

import commands.Command;

import java.util.List;

public class CommandExit implements Command {

    @Override
    public String run(List<String> arguments) {
        System.exit(0);
        return null;
    }
}
