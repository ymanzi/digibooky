package com.switchfully.digibooky.member.domain.exceptions;

public class InvalidINSSFormatException extends RuntimeException {
    public InvalidINSSFormatException() {
    }

    public InvalidINSSFormatException(String message) {
        super(message);
    }

    public InvalidINSSFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidINSSFormatException(Throwable cause) {
        super(cause);
    }

    public InvalidINSSFormatException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
