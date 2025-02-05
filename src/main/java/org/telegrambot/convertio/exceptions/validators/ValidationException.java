package org.telegrambot.convertio.exceptions.validators;

import org.telegrambot.convertio.exceptions.AppException;

public class ValidationException extends AppException {
    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationException(Throwable cause) {
        super(cause);
    }
}
