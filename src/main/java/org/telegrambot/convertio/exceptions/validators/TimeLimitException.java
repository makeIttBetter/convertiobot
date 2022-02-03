package org.telegrambot.convertio.exceptions.validators;

public class TimeLimitException extends ValidationException {

    public TimeLimitException() {
        super();
    }

    public TimeLimitException(String message) {
        super(message);
    }

    public TimeLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public TimeLimitException(Throwable cause) {
        super(cause);
    }

}
