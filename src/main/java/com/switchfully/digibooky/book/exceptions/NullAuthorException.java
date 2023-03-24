package com.switchfully.digibooky.book.exceptions;

public class NullAuthorException extends RuntimeException{
    public NullAuthorException() {
        super("The author can not be null");
    }
}
