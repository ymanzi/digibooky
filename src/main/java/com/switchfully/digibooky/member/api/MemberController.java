package com.switchfully.digibooky.member.api;

import com.switchfully.digibooky.member.domain.exceptions.EmailAlreadyExistsException;
import com.switchfully.digibooky.member.domain.exceptions.INSSAlreadyExistsException;
import com.switchfully.digibooky.member.domain.exceptions.InvalidFormatException;
import com.switchfully.digibooky.member.domain.exceptions.MissingException;
import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import com.switchfully.digibooky.member.service.MemberService;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/members")
public class MemberController {
    private final MemberService service;
    Logger logger = LoggerFactory.getLogger(MemberController.class);

    public MemberController(MemberService service) {
        this.service = service;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<MemberDto> getAllMembers(@RequestHeader String id){
        return service.getAllMembers(id);
    }
    @GetMapping(path = "/admin")
    @ResponseStatus(HttpStatus.OK)
    public void getAdminId(){
        logger.info(service.getAdminId());
    }
    @PostMapping(path = "/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerMember(@RequestBody CreateMemberDto memberDto){
        return service.registerMember(memberDto);
    }
    @PostMapping(path = "/register/librarian")
    @ResponseStatus(HttpStatus.CREATED)
    public MemberDto registerLibrarian(@RequestBody CreateMemberDto memberDto, @RequestHeader String id){
        return service.registerLibrarian(memberDto, id);
    }
    @ExceptionHandler({MissingException.class, InvalidFormatException.class})
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String handleMissingExceptionsAndFormatExceptions(Exception exception) {
        return exception.getMessage();
    }
    @ExceptionHandler({EmailAlreadyExistsException.class, INSSAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleAlreadyExistExceptions(Exception exception){
        return exception.getMessage();
    }
    @ExceptionHandler(NoRightException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleNoRightException(Exception exception){
        return exception.getMessage();
    }
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleExceptions(Exception exception){
        logger.error(exception.getMessage(),exception);
    }
}
