package org.telegrambot.convertio.exceptions.validators;

public class IncorrectHtmlSyntaxException extends ValidationException {
    public IncorrectHtmlSyntaxException() {
        super();
    }

    public IncorrectHtmlSyntaxException(String message) {
        super(message);
    }

    public IncorrectHtmlSyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectHtmlSyntaxException(Throwable cause) {
        super(cause);
    }
}
