package com.switchfully.digibooky.book.exceptions;


public class NoTitleForBookException extends RuntimeException{
    public NoTitleForBookException() {
        super("The book's title field can't be empty");
    }
}