package commands;

import java.util.List;

public interface Command {
    String run(List<String> arguments);
}
