package com.switchfully.digibooky.member.domain.exceptions;

public class LastNameMissingException extends MissingException {
    public LastNameMissingException() {
    }

    public LastNameMissingException(String message) {
        super(message);
    }

    public LastNameMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public LastNameMissingException(Throwable cause) {
        super(cause);
    }
}
