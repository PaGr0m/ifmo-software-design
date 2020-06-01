package service;

import java.util.Scanner;

// TODO: exception
public class Shell {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Executor executor = new Executor();

    public static void run() {
        String input;
        String output;

        System.out.print("SHELL >> ");
        while (scanner.hasNextLine()) {
            input = scanner.nextLine();
            output = executor.execute(input);

            System.out.println(output);
            System.out.print("SHELL >> ");
        }
    }
}
