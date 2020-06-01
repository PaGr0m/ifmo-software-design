package commands;

public class CommandException extends RuntimeException {
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public CommandException(Throwable throwable) {
        super(throwable);
    }

    protected CommandException(String message,
                               Throwable throwable,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, throwable, enableSuppression, writableStackTrace);
    }
}
