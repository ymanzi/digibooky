package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AuthorTest {
    @Test
    void createAuthorWithAllTheFields_thenCreateTheCorrespondingObject() {
        //When
        Author author = new Author("firstname", "lastname");

        //Then
        assertThat(author.getFirstname()).isEqualTo("firstname");
        assertThat(author.getLastname()).isEqualTo("lastname");
    }

    @Test
    void createAuthorWithEmptyLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Author("firstname", ""));
    }

    @Test
    void createAuthorWithBlankLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Author("firstname", "    "));
    }

    @Test
    void createAuthorWithNullLastname_thenRaiseNoLastnameForAuthorException() {
        //Then
        assertThrows(NoLastnameForAuthorException.class, () -> new Author("firstname", null));
    }
}
