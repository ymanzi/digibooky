package com.switchfully.digibooky.rental.domain;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.rental.service.exceptions.NoSuchRentalException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RentalRepositoryTest {
    private RentalRepository rentalRepository;
    private Book book;
    private Member member;
    private Rental rental;

    @BeforeEach
    void setUp() {
        rentalRepository = new RentalRepository();
        book = new Book("52187", "Java for dummies", new Author("Barbara", "Liskov"), "A oriented object programming book");

        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("Bill")
                .withLastName("Gates")
                .withEmail("bill@microsoft.com")
                .withAddress(new Address("", "", "", "Seattle"))
                .build();
        rental = new Rental(book, member);
        rentalRepository.save(rental);
    }

    @Test
    void testGetAllRentals(){
        Book book2 = new Book("5678", "Database for dummies", new Author("Luc", "Perkins"), "A good book for beginners in datascience");
        Member member2 = new Member.MemberBuilder()
                .withINSS("582-346-917")
                .withFirstname("Henri")
                .withLastName("GVS")
                .withEmail("henrigevenois@caramail.com")
                .withRole(Role.MEMBER)
                .build();
        Rental rental2 = new Rental(book2, member2);
        rentalRepository.save(rental2);
        Collection<Rental> allRentals = rentalRepository.getAllRentals();
        assertEquals(2, allRentals.size());
        assertTrue(allRentals.contains(rental));
        assertTrue(allRentals.contains(rental2));
    }

    @Test
    void Save_WhenARentalIsSaved_ThenRepoContainsThisRental() {
        Rental savedRental = rentalRepository.getRentalById(rental.getRentalId());
        assertThat(rentalRepository.getAllRentals().contains(savedRental));
    }

    @Test
    void getRentalById_whenARentalIdIsProvided_thenReturnTheRental() {
        UUID rentalUuid = rental.getRentalId();
        assertEquals(rental, rentalRepository.getRentalById(rentalUuid));
    }

    @Test
    void getRentalById_whenAnUnknownRentalIdIsProvided_thenThrowsAnException(){
        UUID unknownRentalID = UUID.randomUUID();
        assertThrows(NoSuchRentalException.class, () -> rentalRepository.getRentalById(unknownRentalID));
    }

    @Test
    void deleteRental_whenTheRentalProvidedExists_ThenItIsRemovedFromRepository(){
        rentalRepository.deleteRental(rental);
        assertFalse(rentalRepository.getAllRentals().contains(rental));
    }

    @Test
    void whenRentalNotFoundForDeletion_thenRentalRepositoryThrowsException_() {
        Book book2 = new Book("5678", "Database for dummies", new Author("Luc", "Perkins"), "A good book for beginners in datascience");
        Member member2 = new Member.MemberBuilder()
                .withINSS("582-346-917")
                .withFirstname("Henri")
                .withLastName("GVS")
                .withEmail("henrigevenois@caramail.com")
                .withRole(Role.MEMBER)
                .build();
        Rental unknownRental = new Rental(book2, member2);
        assertThrows(NoSuchRentalException.class, () -> rentalRepository.deleteRental(unknownRental));
    }
}
