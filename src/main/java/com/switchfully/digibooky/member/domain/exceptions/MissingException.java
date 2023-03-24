package com.switchfully.digibooky.member.domain.exceptions;

public class MissingException extends RuntimeException{
    public MissingException() {
    }

    public MissingException(String message) {
        super(message);
    }

    public MissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingException(Throwable cause) {
        super(cause);
    }
}
