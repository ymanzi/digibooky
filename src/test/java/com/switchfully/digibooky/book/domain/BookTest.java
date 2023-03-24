package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;
import com.switchfully.digibooky.book.exceptions.NoTitleForBookException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookTest {

    @Test
    void createBookWithEmptyTitle_thenRaiseNoTitleForBookException() {
        //Then
        assertThrows(NoTitleForBookException.class, () -> new Book("isbn", "", new Author("first", "last"), "summary"));
    }

    @Test
    void createBookWithBlankLastname_thenRaiseNoTitleForBookException() {
        //Then
        assertThrows(NoTitleForBookException.class, () -> new Book("isbn", "    ", new Author("first", "last"), "summary"));
    }

    @Test
    void createBookWithNullLastname_thenRaiseNoTitleForBookException() {
        //Then
        assertThrows(NoTitleForBookException.class, () -> new Book("isbn", null, new Author("first", "last"), "summary"));
    }

    @Test
    void createBookWithEmptyAuthorLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Book("isbn", "title", new Author("first", ""), "summary"));
    }

    @Test
    void createBookWithBlankAuthorLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Book("isbn", "title", new Author("first", "     "), "summary"));
    }

    @Test
    void createBookWithNullAuthorLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Book("isbn", "title", new Author("first", null), "summary"));
    }

}
