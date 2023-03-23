package com.switchfully.digibooky.rental.service.exceptions;

public class NoSuchBookInStoreException extends RuntimeException {
    public NoSuchBookInStoreException() {
    }

    public NoSuchBookInStoreException(String message) {
        super(message);
    }

    public NoSuchBookInStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchBookInStoreException(Throwable cause) {
        super(cause);
    }
}
