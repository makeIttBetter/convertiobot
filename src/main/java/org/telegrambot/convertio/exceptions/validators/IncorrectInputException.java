package org.telegrambot.convertio.exceptions.validators;

public class IncorrectInputException extends ValidationException {
    public IncorrectInputException() {
        super();
    }

    public IncorrectInputException(String message) {
        super(message);
    }

    public IncorrectInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectInputException(Throwable cause) {
        super(cause);
    }
}
