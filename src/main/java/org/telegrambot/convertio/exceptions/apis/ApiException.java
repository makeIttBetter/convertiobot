package org.telegrambot.convertio.exceptions.apis;

import org.telegrambot.convertio.exceptions.AppException;

public class ApiException extends AppException {
    public ApiException() {
        super();
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
