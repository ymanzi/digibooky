package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;

import java.util.Objects;
import java.util.UUID;

public class Author {
    private final String firstname;
    private final String lastname;

    //assertThrows
    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        if (lastname == null || lastname.isEmpty() || lastname.isBlank())
            throw new NoLastnameForAuthorException();
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public UUID getUserId() { return authorId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(firstname, author.firstname) && Objects.equals(lastname, author.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
