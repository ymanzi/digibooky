package com.switchfully.digibooky.rental.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NoSuchMemberException extends RuntimeException {
    public NoSuchMemberException() {
    }

    public NoSuchMemberException(String message) {
        super(message);
    }

    public NoSuchMemberException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchMemberException(Throwable cause) {
        super(cause);
    }
}
