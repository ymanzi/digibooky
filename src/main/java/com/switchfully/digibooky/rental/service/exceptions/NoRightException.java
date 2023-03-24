package com.switchfully.digibooky.rental.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NoRightException extends RuntimeException {
    public NoRightException() {
    }

    public NoRightException(String message) {
        super(message);
    }

    public NoRightException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoRightException(Throwable cause) {
        super(cause);
    }
}
