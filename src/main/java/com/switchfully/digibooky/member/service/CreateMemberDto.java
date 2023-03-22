package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Role;

public class CreateMemberDto {
    private final String INSS;
    private final String firstname;
    private final String lastName;
    private final String email;
    private final Address address;
    private final Role role;

    private CreateMemberDto(String INSS, String firstname, String lastName, String email, Address address, Role role) {
        this.INSS = INSS;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
    }
    public String getINSS(){
        return INSS;
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

    public Role getRole() {
        return role;
    }
}
