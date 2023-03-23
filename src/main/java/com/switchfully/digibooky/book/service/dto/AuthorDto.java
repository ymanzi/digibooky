package com.switchfully.digibooky.book.service.dto;

import java.util.Objects;

public class AuthorDto {
    private final String firstname;
    private final String lastname;

    public AuthorDto(String firstname, String lastname) {
        this.firstname = firstname;
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
        AuthorDto authorDto = (AuthorDto) o;
        return firstname.equals(authorDto.firstname) && lastname.equals(authorDto.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname);
    }
}
