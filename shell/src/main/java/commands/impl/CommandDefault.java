package commands.impl;

import commands.Command;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CommandDefault implements Command {

    @Override
    public String run(List<String> arguments) {
//        String args = String.join(" ", arguments);
//
//        ProcessBuilder processBuilder = new ProcessBuilder();
//        processBuilder.command(name, args);
//
//        try {
//            Process process = processBuilder.start();
//            StringBuilder output = new StringBuilder();
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line).append("\n");
//            }
//
//            int exitVal = process.waitFor();
//            if (exitVal == 0) {
////                System.out.println("Success!");
////                System.out.println(output);
//                System.exit(0);
//            }
//
//        } catch (IOException | InterruptedException e) {
//            e.printStackTrace();
//        }
//
        return "";
//    }
    }
}
