package com.switchfully.digibooky.member.domain.exceptions;

public class INSSAlreadyExistsException extends RuntimeException {
    public INSSAlreadyExistsException() {
    }

    public INSSAlreadyExistsException(String message) {
        super(message);
    }

    public INSSAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public INSSAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
