package com.switchfully.digibooky.book.exceptions;

public class NoIsbnForBookException extends RuntimeException{
    public NoIsbnForBookException() {
        super("The book's isbn field can't be empty");
    }
}
