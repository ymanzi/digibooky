package com.switchfully.digibooky.member.domain.exceptions;

public class InvalidFormatException extends RuntimeException{
    public InvalidFormatException() {
    }

    public InvalidFormatException(String message) {
        super(message);
    }

    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidFormatException(Throwable cause) {
        super(cause);
    }
}
