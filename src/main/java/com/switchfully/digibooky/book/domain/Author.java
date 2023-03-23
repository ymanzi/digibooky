package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;

import java.util.Objects;

public class Author {
    private final int userId;
    private static int userNumber = 1;
    private final String firstname;
    private final String lastname;

    //assertThrows
    public Author(String firstname, String lastname) {
        this.firstname = firstname;
        /*if (lastname == null || lastname.isEmpty() || lastname.isBlank())
            throw new NoAuthortLastnameException();*/
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public int getUserId() { return userId;}

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
