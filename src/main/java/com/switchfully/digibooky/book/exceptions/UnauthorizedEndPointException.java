package com.switchfully.digibooky.book.exceptions;

public class UnauthorizedEndPointException extends RuntimeException{
    public UnauthorizedEndPointException() {
        super("Unauthorized End Point!");
    }
}
