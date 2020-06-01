package commands.impl;

import commands.Command;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class CommandWc implements Command {

    @Override
    public String run(List<String> arguments) {
        String args = String.join(" ", arguments);

        int totalWords = 0;
        int totalLines = 0;
        int totalBytes = 0;

        List<String> output = new ArrayList<>();
        String[] files = args.split(" ");
        for (String fileName : files) {
            int words = 0;
            int lines = 0;
            int bytes = 0;

            File file = new File(fileName);

            if (file.isDirectory())
                return "This path is directory!";

            if (!file.isFile()) {
                Scanner scanner = new Scanner(args);
                while (Objects.requireNonNull(scanner).hasNextLine()) {
                    List<String> line = Arrays.asList(scanner.nextLine().split(" "));
                    bytes += line.stream().mapToInt(String::length).sum();
                    words += line.size();
                    lines++;
                }
                output.add(lines + " " + words + " " + bytes + "\n");

                return String.join("", output);
            }

            Scanner scanner;
            try {
                scanner = new Scanner(file);
            } catch (FileNotFoundException e) {
                return "No such file";
            }

            while (Objects.requireNonNull(scanner).hasNextLine()) {
                List<String> line = Arrays.asList(scanner.nextLine().split(" "));
                bytes += line.stream().mapToInt(String::length).sum();
                words += line.size();
                lines++;
            }
            totalBytes += bytes;
            totalLines += lines;
            totalWords += words;
            scanner.close();

            output.add(lines + " " + words + " " + bytes + " " + file.getPath() + "\n");
        }

        if (files.length > 1) {
            output.add(totalLines + " " + totalWords + " " + totalBytes + " " + "total" + "\n");
        }

        return String.join("", output);
    }
}
