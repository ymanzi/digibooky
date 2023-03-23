package com.switchfully.digibooky.rental.service.exceptions;

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
