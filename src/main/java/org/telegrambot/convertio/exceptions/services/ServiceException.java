package org.telegrambot.convertio.exceptions.services;

import org.telegrambot.convertio.exceptions.AppException;

public class ServiceException extends AppException {
    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

}
