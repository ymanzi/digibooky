package com.switchfully.digibooky.member.domain;

import com.switchfully.digibooky.member.domain.exceptions.*;
import com.switchfully.digibooky.rental.domain.Rental;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Member {
    private final UUID id;
    private final String inss;
    private String firstname;
    private String lastName;
    private String email;
    private Address address;
    private Role role;
    private final List<Rental> rentedBooks = new ArrayList<>();

    private Member(String inss, String firstname, String lastName, String email, Address address, Role role) {
        id = UUID.randomUUID();
        this.inss = inss;
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public UUID getId() {
        return id;
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

    public Role getRole() {
        return role;
    }

    public List<Rental> getRentedBooks() {
        return rentedBooks;
    }
    public void rentBook(Rental rental){
        rentedBooks.add(rental);
    }
    public void returnBook(Rental rental){
        rentedBooks.remove(rental);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.id) && inss.equals(member.inss) && Objects.equals(firstname, member.firstname) && lastName.equals(member.lastName) && email.equals(member.email) && address.equals(member.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inss, firstname, lastName, email, address);
    }
    public static class MemberBuilder {
        private String INSS;
        private String firstname;
        private String lastName;
        private String email;
        private Address address;
        private Role role;

        public MemberBuilder withINSS(String INSS) {
            Pattern pattern = Pattern.compile("^([0-9]{3}-){2,3}[0-9]{3}$");
            Matcher matcher = pattern.matcher(INSS);
            if (matcher.matches())
                this.INSS = INSS;
            else
                throw new InvalidINSSFormatException("Invalid INSS provided: " + INSS );
            return this;
        }

        public MemberBuilder withFirstname(String firstname) {
            this.firstname = firstname;
            return this;
        }

        public MemberBuilder withLastName(String lastName) {
            if (lastName == null || lastName.isEmpty())
                throw new LastNameMissingException("Last name is missing");
            this.lastName = lastName;
            return this;
        }

        public MemberBuilder withEmail(String email) {
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+@[a-z]+[.][a-z]+$");
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches())
                this.email = email;
            else
                throw new InvalidEmailFormatException("Invalid email: " + email);
            return this;
        }

        public MemberBuilder withAddress(Address address) {
            if (address == null|| address.city() == null || address.city().isEmpty())
                throw new CityInAddressMissingException("Missing city in address");
            this.address = address;
            return this;
        }
        public MemberBuilder withRole(Role role){
            this.role = role;
            return this;
        }
        public Member build(){
            if (INSS == null)
                throw new INSSMissingException();
            if (email == null)
                throw new EmailMissingException();
            if (lastName == null)
                throw new LastNameMissingException();
            return new Member(INSS,firstname,lastName,email,address,role);
        }
    }

}
