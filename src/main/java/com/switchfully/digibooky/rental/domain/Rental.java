package com.switchfully.digibooky.rental.domain;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.member.domain.Member;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class Rental {
    private final UUID rentalId;
    private final Book bookRented;
    private final Member renter;
    private final LocalDate returnDate;

    public Rental(Book bookRented, Member renter) {
        this.rentalId = UUID.randomUUID();
        this.bookRented = bookRented;
        this.renter = renter;
        this.returnDate = LocalDate.now().plusWeeks(3);
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public Book getBookRented() {
        return bookRented;
    }

    public Member getRenter() {
        return renter;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rental rental = (Rental) o;
        return Objects.equals(rentalId, rental.rentalId) && Objects.equals(bookRented, rental.bookRented) && Objects.equals(renter, rental.renter) && Objects.equals(returnDate, rental.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, bookRented, renter, returnDate);
    }
}
