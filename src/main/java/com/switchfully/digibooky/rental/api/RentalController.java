package com.switchfully.digibooky.rental.api;

import com.switchfully.digibooky.rental.service.RentalService;
import com.switchfully.digibooky.rental.service.dto.RentalDto;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
import jdk.jfr.StackTrace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rental")
public class RentalController {
    private final RentalService rentalService;
    Logger logger = LoggerFactory.getLogger(RentalController.class);
    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getAllRentals(@RequestHeader String userId){
        return rentalService.getAllRentals(userId);
    }

    @PostMapping(path = "/rentBook")
    @ResponseStatus(HttpStatus.CREATED)
    public RentalDto rentBook(@RequestHeader String ISBN, @RequestHeader String userId){
        return rentalService.rentBook(ISBN, userId);
    }

    @PostMapping(path = "/returnBook")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String returnBook(@RequestBody String rentalId){
        return rentalService.returnBook(rentalId);
    }

    @GetMapping(path = "/{memberId}")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getAllRentalsForSpecificMember(@PathVariable String memberId, @RequestHeader String userId){
        return rentalService.getAllRentalsForSpecificMember(memberId, userId);
    }
    @GetMapping(path = "/overdue")
    @ResponseStatus(HttpStatus.OK)
    public List<RentalDto> getAllOverdueRentals(@RequestHeader String userId){
        return rentalService.getAllOverdueRentals(userId);
    }
    @ExceptionHandler(NoRightException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public void handleNoRightException(Exception exception){
        logger.error(exception.getMessage(), exception);
    }
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleException(Exception exception){
        logger.error(exception.getMessage(), exception);
    }
}
