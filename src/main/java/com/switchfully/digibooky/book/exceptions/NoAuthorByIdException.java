package com.switchfully.digibooky.book.exceptions;

public class NoAuthorByIdException extends RuntimeException{
    public NoAuthorByIdException() {
        super("This member doesn't exist!");
    }
}
