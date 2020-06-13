package service;

import commands.CommandException;

import java.util.Scanner;

/**
 * Обработчик командной строки
 */
public class Shell {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Executor executor = new Executor();
    private static final String repl = "SHELL >> ";

    /**
     * Запуск приложения
     *
     * <p> input считанная строка </p>
     * <p> output вывод программы </p>
     */
    public static void run() {
        String input;
        String output;

        System.out.print(repl);
        while (scanner.hasNextLine()) {
            try {
                input = scanner.nextLine();
                output = executor.execute(input);

                System.out.println(output);
            } catch (CommandException exception) {
                System.out.println(exception.getMessage());
            } finally {
                System.out.print(repl);
            }
        }
    }
}
