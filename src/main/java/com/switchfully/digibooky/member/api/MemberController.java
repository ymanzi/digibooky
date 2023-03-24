package com.switchfully.digibooky.member.api;

import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import com.switchfully.digibooky.member.service.MemberService;
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
}
