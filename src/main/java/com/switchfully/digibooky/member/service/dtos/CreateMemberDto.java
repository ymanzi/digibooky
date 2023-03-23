package com.switchfully.digibooky.member.service.dtos;

import com.switchfully.digibooky.member.domain.Address;

public class CreateMemberDto {
    private final String inss;
    private final String firstname;
    private final String lastName;
    private final String email;
    private final Address address;

    public CreateMemberDto(String inss, String firstname, String lastName, String email, Address address) {
        this.inss = inss;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
    }
    public String getInss(){
        return inss;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }
}
