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

    @Test
    void givenTwoNewAuthor_thenTheirIdNumberWillNotBeTheSame(){
        Author author1 = new Author("firstname1", "lastname1");
        Author author2 = new Author("firstname2", "lastname2");
        Author author3 = new Author("firstname2", "lastname2");

        assertThat(author1.getUserId()).isNotEqualTo(author2.getUserId());
        assertThat(author2.getUserId()).isNotEqualTo(author3.getUserId());
    }
}
