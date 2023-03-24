package com.switchfully.digibooky.member.domain.exceptions;

public class EmailMissingException extends MissingException {
    public EmailMissingException() {
    }

    public EmailMissingException(String message) {
        super(message);
    }

    public EmailMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailMissingException(Throwable cause) {
        super(cause);
    }
}
