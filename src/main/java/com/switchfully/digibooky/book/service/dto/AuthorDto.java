package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;

import java.util.Objects;

public class AuthorDto {
    private final String firstname;
    private final String lastname;

    //assertThrows
    public AuthorDto(String firstname, String lastname) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDto author = (AuthorDto) o;
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
