package com.switchfully.digibooky.book.exceptions;

public class NoBookByIsbnException extends RuntimeException {
    public NoBookByIsbnException() {
        super("There is no book with the requested Isbn");
    }
}
