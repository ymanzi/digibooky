package com.switchfully.digibooky.member.domain.exceptions;

public class CityInAddressMissingException extends RuntimeException {
    public CityInAddressMissingException() {
    }

    public CityInAddressMissingException(String message) {
        super(message);
    }

    public CityInAddressMissingException(String message, Throwable cause) {
        super(message, cause);
    }

    public CityInAddressMissingException(Throwable cause) {
        super(cause);
    }
}
