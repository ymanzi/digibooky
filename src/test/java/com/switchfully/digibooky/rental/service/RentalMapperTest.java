package com.switchfully.digibooky.rental.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.rental.domain.Rental;
import com.switchfully.digibooky.rental.service.dto.CreateRentalDto;
import com.switchfully.digibooky.rental.service.dto.RentalDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RentalMapperTest {

    private Book book;
    private Book book2;
    private Member member;
    private Member member2;

    @BeforeEach
    void setup(){
        book = new Book("52187", "Java for dummies", new Author("Barbara", "Liskov"), "A oriented object programming book");
        book2 = new Book("58416", "SQL for dummies", new Author("Jean-Luc", "Hainaut"), "A structured and normalized book");

        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("Bill")
                .withLastName("Gates")
                .withEmail("bill@microsoft.com")
                .withAddress(new Address("", "", "", "Seattle"))
                .build();
        member2 = new Member.MemberBuilder()
                .withINSS("547-349-364")
                .withFirstname("Elon")
                .withLastName("Musk")
                .withEmail("elon@tesla.mars")
                .withAddress(new Address("", "", "", "Model-S"))
                .build();
    }

    @Test
    void fromDTO_whenAValidCreateRentalDTO_thenReturnRental(){
        CreateRentalDto createRentalDto = new CreateRentalDto(book, member);
        RentalMapper rentalMapper = new RentalMapper();
        Rental rental = rentalMapper.fromDTO(createRentalDto);

        assertNotNull(rental.getRentalId());
        assertEquals(book, rental.getBookRented());
        assertEquals(member, rental.getRenter());
    }

    @Test
    void toDto_whenAValidRental_thenReturnRentalDTO(){
        Rental rental = new Rental(book, member);
        RentalMapper rentalMapper = new RentalMapper();
        RentalDto rentalDto = rentalMapper.toDto(rental);
        assertNotNull(rentalDto.getRentalId());
        assertEquals(rental.getReturnDate(), rentalDto.getReturnDate());
        assertEquals(rental.getRenter(), rentalDto.getRenter());
        assertEquals(rental.getBookRented(), rentalDto.getBookRented());
    }

    @Test
    void toDtoList_whenValidRentals_thenReturnRentalDtoList(){
        Rental rental1 = new Rental(book, member);
        Rental rental2 = new Rental(book2, member2);
        RentalMapper rentalMapper = new RentalMapper();
        List<Rental> rentals = List.of(rental1, rental2);
        List<RentalDto> rentalDtos = rentalMapper.toDtoList(rentals);

        assertEquals(2, rentalDtos.size());
        assertEquals(rental1.getRentalId(), rentalDtos.get(0).getRentalId());
        assertEquals(rental2.getRentalId(), rentalDtos.get(1).getRentalId());
        assertEquals(rental1.getRenter(), rentalDtos.get(0).getRenter());
        assertEquals(rental2.getRenter(), rentalDtos.get(1).getReturnDate());
        assertEquals(rental1.getReturnDate(), rentalDtos.get(0).getReturnDate());
        assertEquals(rental2.getReturnDate(), rentalDtos.get(1).getReturnDate());
        assertEquals(rental1.getBookRented(), rentalDtos.get(0).getBookRented());
        assertEquals(rental2.getBookRented(), rentalDtos.get(1).getBookRented());
    }


}
