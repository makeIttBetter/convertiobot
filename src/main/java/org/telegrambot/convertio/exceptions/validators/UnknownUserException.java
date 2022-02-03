package org.telegrambot.convertio.exceptions.validators;

public class UnknownUserException extends ValidationException {
    public UnknownUserException() {
        super();
    }

    public UnknownUserException(String message) {
        super(message);
    }

    public UnknownUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownUserException(Throwable cause) {
        super(cause);
    }
}
