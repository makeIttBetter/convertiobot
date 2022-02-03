package org.telegrambot.convertio.exceptions.validators;

public class IncorrectUsernameException extends ValidationException {
    public IncorrectUsernameException() {
        super();
    }

    public IncorrectUsernameException(String message) {
        super(message);
    }

    public IncorrectUsernameException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectUsernameException(Throwable cause) {
        super(cause);
    }
}
