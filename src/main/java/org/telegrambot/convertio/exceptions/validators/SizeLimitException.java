package org.telegrambot.convertio.exceptions.validators;

public class SizeLimitException extends ValidationException {
    public SizeLimitException() {
        super();
    }

    public SizeLimitException(String message) {
        super(message);
    }

    public SizeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public SizeLimitException(Throwable cause) {
        super(cause);
    }
}
