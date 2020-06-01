package commands.impl;

import commands.Command;

import java.util.List;

public class CommandPwd implements Command {

    @Override
    public String run(List<String> arguments) {
        return System.getProperty("user.dir");
    }
}
