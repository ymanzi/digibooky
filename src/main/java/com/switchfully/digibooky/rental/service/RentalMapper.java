package com.switchfully.digibooky.rental.service;

import com.switchfully.digibooky.book.service.mapper.BookMapper;
import com.switchfully.digibooky.member.service.MemberMapper;
import com.switchfully.digibooky.rental.domain.Rental;
import com.switchfully.digibooky.rental.service.dto.CreateRentalDto;
import com.switchfully.digibooky.rental.service.dto.RentalDto;

import java.util.Collection;
import java.util.List;

public class RentalMapper {
    private final BookMapper bookMapper = new BookMapper();
    private final MemberMapper memberMapper = new MemberMapper();
    public Rental fromDTO(CreateRentalDto rentalDTO){
        return new Rental(rentalDTO.getBookRented(), rentalDTO.getRenter());
    }
    public RentalDto toDto(Rental rental){
        return new RentalDto(rental.getRentalId(),bookMapper.toDto(rental.getBookRented()),memberMapper.toDto(rental.getRenter()),rental.getReturnDate());
    }
    public List<RentalDto>toDtoList(Collection<Rental> rentals){
        return rentals.stream().map(rental -> toDto(rental)).toList();
    }
}
