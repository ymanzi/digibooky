package com.switchfully.digibooky.rental.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchRentalException extends RuntimeException {
    public NoSuchRentalException() {
    }

    public NoSuchRentalException(String s) {
    }

    public NoSuchRentalException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchRentalException(Throwable cause) {
        super(cause);
    }
}
