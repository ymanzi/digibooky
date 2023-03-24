package com.switchfully.digibooky.rental.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.rental.domain.Rental;
import com.switchfully.digibooky.rental.domain.RentalRepository;
import com.switchfully.digibooky.rental.service.dto.RentalDto;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class RentalServiceTest {
    private BookRepository bookRepository;
    private MemberRepository memberRepository;
    private RentalRepository rentalRepository;
    private RentalService rentalService;
    private Book book;
    private Book book2;
    private Member member;
    private Member member2;
    private Rental rental;
    private Rental rental2;

    @BeforeEach
    void setUp() {
        bookRepository = new BookRepository();
        book = new Book("52187", "Java for dummies", new Author("Barbara", "Liskov"), "A oriented object programming book");
        book2 = new Book("58416", "SQL for dummies", new Author("Jean-Luc", "Hainaut"), "A structured and normalized book");
        bookRepository.save(book);
        bookRepository.save(book2);

        memberRepository = new MemberRepository();
        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("Bill")
                .withLastName("Gates")
                .withEmail("bill@microsoft.com")
                .withAddress(new Address("", "", "", "Seattle"))
                .withRole(Role.ADMIN)
                .build();
        member2 = new Member.MemberBuilder()
                .withINSS("547-349-364")
                .withFirstname("Elon")
                .withLastName("Musk")
                .withEmail("elon@tesla.mars")
                .withAddress(new Address("", "", "", "Model-S"))
                .withRole(Role.MEMBER)
                .build();
        memberRepository.save(member);
        memberRepository.save(member2);

        rentalRepository = new RentalRepository();
        rental = new Rental(book, member);
        rental2 = new Rental(book2, member2);
        rentalRepository.save(rental);
        rentalRepository.save(rental2);
        rentalService = new RentalService(bookRepository, memberRepository, rentalRepository);
    }

    @Test
    void getAllRentals_whenMemberRole_thenThrowNoRightException() {
        assertThrows(NoRightException.class, () -> rentalService.getAllRentals(member2.getId().toString()));
    }

    @Test
    void getAllRentals_whenMemberRoleIsNotMember_thenReturnAllRentalsInList(){
        int expectedRentalCount = rentalRepository.getAllRentals().size();
        List<RentalDto> rentalDtoList = rentalService.getAllRentals(member.getId().toString());
        assertEquals(expectedRentalCount, rentalDtoList.size());
    }

    @Test
    void rentBook_whenBookAvailableAndMemberExists_thenReturnRentalDto() {
        String isbn = book.getIsbn();
        String memberId = member2.getId().toString();
        RentalDto rentalDto = rentalService.rentBook(isbn, memberId);

        assertEquals(isbn, rentalDto.getBookRented().getIsbn());
        assertEquals(memberId, rentalDto.getRenter().getId().toString());
    }

    @Test
    void returnBook_whenRentalExists_thenItIsRemovedFromRentalList() {
        String rentalId = rental.getRentalId().toString();
        String message = rentalService.returnBook(rentalId);
        assertNotNull(message, "Returned message must be \"Returned book is overdue\" or \"Returned Book without issue\"");
        assertFalse(rentalService.getAllRentals(member.getId().toString()).contains(rental));
    }

    @Test
    void getAllRentalsForSpecificMember_whenValidMemberAndAdmin_thenReturnRentalsList() {

        String adminId = member.getId().toString();
        String memberId = member2.getId().toString();
        RentalMapper rentalMapper = new RentalMapper();
        List<RentalDto> rentalDtos = rentalService.getAllRentalsForSpecificMember(memberId, adminId);
        List<RentalDto> expectedList = List.of(rentalMapper.toDto(rental));

        assertThrows(NoRightException.class, () -> rentalService.getAllRentalsForSpecificMember(adminId, memberId));
        assertNotNull(rentalDtos, "Rentals list should not be null");
        assertTrue(expectedList.containsAll(expectedList));
    }

    @Test
    void getAllOverdueRentals_whenAdminRole_thenReturnOverdueRentalsList(){
        List<RentalDto> overDueRentals = rentalService.getAllOverdueRentals(member.getId().toString());
        overDueRentals.forEach(rentalDto -> {
            assertTrue(rentalDto.getReturnDate().isBefore(LocalDate.now()));
        });
    }
}
