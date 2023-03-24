package com.switchfully.digibooky.member.domain.exceptions;

public class INSSMissingException extends MissingException {
    public INSSMissingException() {
    }

    public INSSMissingException(String message) {
        super(message);
    }

    public INSSMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public INSSMissingException(Throwable cause) {
        super(cause);
    }
}
