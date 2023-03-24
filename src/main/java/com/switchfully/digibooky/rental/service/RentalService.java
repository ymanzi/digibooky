package com.switchfully.digibooky.rental.service;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.rental.domain.Rental;
import com.switchfully.digibooky.rental.domain.RentalRepository;
import com.switchfully.digibooky.rental.service.dto.RentalDto;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
import com.switchfully.digibooky.rental.service.exceptions.NoSuchBookInStoreException;
import com.switchfully.digibooky.rental.service.exceptions.NoSuchMemberException;
import com.switchfully.digibooky.rental.service.exceptions.NoSuchRentalException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final RentalRepository rentalRepository;
    private final RentalMapper rentalMapper = new RentalMapper();

    public RentalService(BookRepository bookRepository, MemberRepository memberRepository, RentalRepository rentalRepository) {
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
        this.rentalRepository = rentalRepository;
    }

    public List<RentalDto> getAllRentals(String id){
        if (memberRepository.getMemberById(UUID.fromString(id)).getRole() == Role.MEMBER)
            throw new NoRightException("the User with id: " + id + " is not allowed to request all rentals");
        return rentalMapper.toDtoList(rentalRepository.getAllRentals());
    }
    public RentalDto rentBook(String ISBN, String userId){
        Book toRent = bookRepository.getByIsbn(ISBN).get(0);
        if (toRent == null || toRent.isDeleted() || toRent.getAmountInStore() == 0)
            throw new NoSuchBookInStoreException("The book with isbn: " + ISBN + " is not in store");
        Member renter = memberRepository.getMemberById(UUID.fromString(userId));
        if (renter == null)
            throw new NoSuchMemberException("Member with id: " + userId + " does not exist");
        Rental rental = new Rental(toRent,renter);
        rentalRepository.save(rental);
        renter.rentBook(rental);
        toRent.decreaseAmountInStore(1);
        return rentalMapper.toDto(rental);
    }
    public String returnBook(String rentalId){
        Rental rental = rentalRepository.getRentalById(UUID.fromString(rentalId));
        rentalRepository.deleteRental(rental);
        rental.getRenter().returnBook(rental);
        rental.getBookRented().increaseAmountInStore(1);
        if (rental.getReturnDate().isBefore(LocalDate.now())){
            return "Returned book is overdue";
        }
        return "Returned Book without issue";
    }
    public List<RentalDto> getAllRentalsForSpecificMember(String memberId, String userId) {
        if (memberRepository.getMemberById(UUID.fromString(userId)).getRole() == Role.MEMBER)
            throw new NoRightException("User with id: " + userId + " has no right to request rentals");
        List<Rental> rentals = memberRepository.getMemberById(UUID.fromString(memberId)).getRentedBooks();
        return rentalMapper.toDtoList(rentals);
    }

    public List<RentalDto> getAllOverdueRentals(String userId) {
        if (memberRepository.getMemberById(UUID.fromString(userId)).getRole() == Role.MEMBER)
            throw new NoRightException("User with id: " + userId + " has no right to request rentals");
        List<Rental> overdueRentals = rentalRepository.getAllRentals().stream().filter(rental -> rental.getReturnDate().isBefore(LocalDate.now())).toList();
        return rentalMapper.toDtoList(overdueRentals);
    }
}
