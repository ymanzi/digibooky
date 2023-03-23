package com.switchfully.digibooky.book.exceptions;

/*public class NoAuthortLastnameException extends RuntimeException {
    public NoAuthortLastnameException() {
        super("An author needs an non-empty lastname!");
    }
}*/

public class NoBookByAuthorException extends RuntimeException {
    public NoBookByAuthorException() {
        super("There is no book with the requested author");
    }
}
