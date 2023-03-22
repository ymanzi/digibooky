package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Role;

import java.util.UUID;

public class MemberDto {
    private final UUID id;
    private final String firstname;
    private final String lastName;
    private final String email;
    private final Address address;
    private final Role role;

    public MemberDto(UUID id, String firstname, String lastName, String email, Address address, Role role) {
        this.id = id;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public UUID getId() {
        return id;
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
