package com.switchfully.digibooky.rental.service.dto;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.member.domain.Member;

import java.util.Objects;

public class CreateRentalDto {
    private final Book bookRented;
    private final Member renter;

    public CreateRentalDto(Book bookRented, Member renter) {
        this.bookRented = bookRented;
        this.renter = renter;
    }

    public Book getBookRented() {
        return bookRented;
    }

    public Member getRenter() {
        return renter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateRentalDto that = (CreateRentalDto) o;
        return Objects.equals(bookRented, that.bookRented) && Objects.equals(renter, that.renter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookRented, renter);
    }
}
