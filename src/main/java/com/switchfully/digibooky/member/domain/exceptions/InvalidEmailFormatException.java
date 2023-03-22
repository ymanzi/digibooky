package com.switchfully.digibooky.member.domain.exceptions;

public class InvalidEmailFormatException extends RuntimeException {
    public InvalidEmailFormatException() {
    }

    public InvalidEmailFormatException(String message) {
        super(message);
    }

    public InvalidEmailFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailFormatException(Throwable cause) {
        super(cause);
    }
}
