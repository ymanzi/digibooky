package com.switchfully.digibooky.rental.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
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
