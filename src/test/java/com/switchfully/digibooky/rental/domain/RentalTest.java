package com.switchfully.digibooky.rental.domain;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RentalTest {

    private Member member;
    private Book book;
    private Rental rental;

    @BeforeEach
    void setup(){
        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("François")
                .withLastName("Pignon")
                .withEmail("francoispignon@gmail.com")
                .withAddress(new Address("Main Street", "1150", "12125", "New-York City"))
                .withRole(Role.MEMBER)
                .build();

        book = new Book("1234567891234", "Harry Potter", new Author("Joanne", "Rowling"), "As the school year gets underway, Harry discovers that his Potions professor, Snape, does not like him. Hagrid reassures Harry that Snape has no reason to dislike him.");
        rental = new Rental(book, member);
    }

    void testConstructorAndGettersWithAllFieldsFilledIn(){
        assertNotNull(rental);
        assertEquals(member, rental.getRenter());
        assertEquals(book, rental.getBookRented());
    }


    @Test
    void testEquals(){
        Book book2 = new Book("5678", "Database for dummies", new Author("Luc", "Perkins"), "A good book for beginners in datascience");
        Member member2 = new Member.MemberBuilder()
                .withINSS("582-346-917")
                .withFirstname("Henri")
                .withLastName("GVS")
                .withEmail("henrigevenois@caramail.com")
                .withRole(Role.MEMBER)
                .build();
        Rental rental2 = new Rental(book2, member2);
        Rental rental3 = rental;
        assertTrue(rental.equals(rental3));
        assertFalse(rental.equals(rental2));
    }
}
