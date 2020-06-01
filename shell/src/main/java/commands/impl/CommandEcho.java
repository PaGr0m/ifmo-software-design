package commands.impl;

import commands.Command;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommandEcho implements Command {

    @Override
    public String run(@NotNull List<String> arguments) {
        StringBuilder result = new StringBuilder();

        for (String word : arguments) {
            if ((word.startsWith("\"") && word.endsWith("\"")) ||
                (word.startsWith("'") && word.endsWith("'"))) {
                word = word.substring(1, word.length() - 1);
            }
            result.append(word).append(" ");
        }

        return result.deleteCharAt(result.length() - 1).toString();
    }
}
