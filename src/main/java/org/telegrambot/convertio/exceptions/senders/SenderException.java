package org.telegrambot.convertio.exceptions.senders;

import org.telegrambot.convertio.exceptions.AppException;

public class SenderException extends AppException {
    public SenderException() {
        super();
    }

    public SenderException(String message) {
        super(message);
    }

    public SenderException(String message, Throwable cause) {
        super(message, cause);
    }

    public SenderException(Throwable cause) {
        super(cause);
    }
}
