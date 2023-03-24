package com.switchfully.digibooky.book.exceptions;

public class BookWithThisIsbnAlreadyExist extends RuntimeException{
    public BookWithThisIsbnAlreadyExist() {
        super("A book with this Isbn already exists ! ");
    }
}
