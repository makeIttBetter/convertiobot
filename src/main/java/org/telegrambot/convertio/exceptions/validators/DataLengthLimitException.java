package org.telegrambot.convertio.exceptions.validators;

public class DataLengthLimitException extends ValidationException {
    public DataLengthLimitException() {
        super();
    }

    public DataLengthLimitException(String message) {
        super(message);
    }

    public DataLengthLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataLengthLimitException(Throwable cause) {
        super(cause);
    }

}
