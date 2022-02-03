package org.telegrambot.convertio.exceptions.commands;

import org.telegrambot.convertio.exceptions.AppException;

public class CommandException extends AppException {
    public CommandException() {
        super();
    }

    public CommandException(String message) {
        super(message);
    }

    public CommandException(String message, Throwable cause) {
        super(message, cause);
    }

    public CommandException(Throwable cause) {
        super(cause);
    }
}

