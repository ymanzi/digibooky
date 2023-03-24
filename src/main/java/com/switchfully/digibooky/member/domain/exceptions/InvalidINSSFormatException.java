package com.switchfully.digibooky.member.domain.exceptions;

public class InvalidINSSFormatException extends InvalidFormatException {
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
}
