package com.switchfully.digibooky.rental.service.dto;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.service.dtos.MemberDto;

import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

public class RentalDto {
    private final UUID rentalId;
    private final BookDto bookRented;
    private final MemberDto renter;
    private final LocalDate returnDate;

    public RentalDto(UUID rentalId, BookDto bookRented, MemberDto renter, LocalDate returnDate) {
        this.rentalId = rentalId;
        this.bookRented = bookRented;
        this.renter = renter;
        this.returnDate = returnDate;
    }

    public UUID getRentalId() {
        return rentalId;
    }

    public BookDto getBookRented() {
        return bookRented;
    }

    public MemberDto getRenter() {
        return renter;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentalDto rentalDto = (RentalDto) o;
        return Objects.equals(rentalId, rentalDto.rentalId) && Objects.equals(bookRented, rentalDto.bookRented) && Objects.equals(renter, rentalDto.renter) && Objects.equals(returnDate, rentalDto.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentalId, bookRented, renter, returnDate);
    }
}
