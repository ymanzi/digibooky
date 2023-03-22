package com.switchfully.digibooky.book.exceptions;

public class NoBookByAuthorException extends RuntimeException {
    public NoBookByAuthorException() {
        super("There is no book with the requested author");
    }
}
