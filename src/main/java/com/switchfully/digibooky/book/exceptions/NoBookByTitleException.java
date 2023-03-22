package com.switchfully.digibooky.book.exceptions;

public class NoBookByTitleException extends RuntimeException {
    public NoBookByTitleException() {
        super("There is no book with the requested title");
    }
}
