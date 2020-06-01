package commands;

public class CommandException extends RuntimeException {
    public CommandException() {
        super();
    }

    public CommandException(String s) {
        super(s);
    }
}
