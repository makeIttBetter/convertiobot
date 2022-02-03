package org.telegrambot.convertio.exceptions.validators;

public class UnknownCurrencyException extends ValidationException {
    public UnknownCurrencyException() {
        super();
    }

    public UnknownCurrencyException(String message) {
        super(message);
    }

    public UnknownCurrencyException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownCurrencyException(Throwable cause) {
        super(cause);
    }
}
