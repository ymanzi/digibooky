package com.switchfully.digibooky.book.exceptions;

public class NoLastnameForAuthorException extends RuntimeException{
    public NoLastnameForAuthorException() {
        super("The author's lastname field can't be empty");
    }
}