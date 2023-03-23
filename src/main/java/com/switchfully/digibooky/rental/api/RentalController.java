package com.switchfully.digibooky.rental.api;

import com.switchfully.digibooky.rental.service.RentalService;
import com.switchfully.digibooky.rental.service.dto.RentalDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rental")
public class RentalController {
    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getAllRentals(@RequestHeader String id){
        return getAllRentals(id);
    }

    @PostMapping(path = "/rentBook")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalDto rentBook(@RequestBody String ISBN, @RequestHeader String userID){
        return rentBook(ISBN, userID);
    }

    @PostMapping(path = "/returnBook")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String returnBook(@RequestBody String rentalID){
        return returnBook(rentalID);
    }
}
