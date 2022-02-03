package org.telegrambot.convertio.exceptions.validators;

public class UnknownPaywayMethodException extends ValidationException {
    public UnknownPaywayMethodException() {
        super();
    }

    public UnknownPaywayMethodException(String message) {
        super(message);
    }

    public UnknownPaywayMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownPaywayMethodException(Throwable cause) {
        super(cause);
    }
}
