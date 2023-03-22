package com.switchfully.digibooky.member.domain;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Member {
    private final UUID id;
    private final String INSS;
    private String firstname;
    private String lastName;
    private String email;
    private Address address;
    private Role role;

    private Member(String INSS, String firstname, String lastName, String email, Address address, Role role) {
        id = UUID.randomUUID();
        this.INSS = INSS;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public UUID getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && INSS.equals(member.INSS) && Objects.equals(firstname, member.firstname) && lastName.equals(member.lastName) && email.equals(member.email) && address.equals(member.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(INSS, firstname, lastName, email, address);
    }
    public static class MemberBuilder {
        private String INSS;
        private String firstname;
        private String lastName;
        private String email;
        private Address address;
        private Role role;

        public MemberBuilder withINSS(String INSS) {
            Pattern pattern = Pattern.compile("^([0-9]-){2,3}[0-9]{3}$");
            Matcher matcher = pattern.matcher(INSS);
            if (matcher.find())
                this.INSS = INSS;
            else
                throw new RuntimeException("placeHolder");
            return this;
        }

        public MemberBuilder withFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public MemberBuilder withLastName(String lastName) {
            if (lastName == null || lastName.isEmpty())
                throw new RuntimeException();
            this.lastName = lastName;
            return this;
        }

        public MemberBuilder withEmail(String email) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-z]+[.][a-z]+$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.find())
                this.email = email;
            else
                throw new RuntimeException("placeholder");
            return this;
        }

        public MemberBuilder withAddress(Address address) {
            if (address == null|| address.city() == null || address.city().isEmpty())
                throw new RuntimeException("placeholder");
            this.address = address;
            return this;
        }
        public MemberBuilder withRole(Role role){
            this.role = role;
            return this;
        }
        public Member build(){
            return new Member(INSS,firstname,lastName,email,address,role);
        }
    }

}
