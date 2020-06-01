package commands.impl;

import commands.Command;
import service.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class CommandCat implements Command {

    @Override
    public String run(List<String> arguments) {
        String args = String.join(" ", arguments);
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(
                Paths.get(Environment.getCurrentPath(), args.trim()))) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.getMessage();
            contentBuilder.append("File is not exist!");
        }

        return contentBuilder.toString();
    }
}
