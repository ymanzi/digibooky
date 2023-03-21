package com.switchfully.digibooky.book.service.dto;

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


}
