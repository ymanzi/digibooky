package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class MemberService {
    private final MemberMapper mapper;
    private final MemberRepository repository;

    public MemberService(MemberRepository repository) {
        this.mapper = new MemberMapper();
        this.repository = repository;
    }
    public List<MemberDto> getAllMembers(String id){
        if (repository.getMemberById(UUID.fromString(id)).getRole() != Role.ADMIN){
            throw new RuntimeException("placeholder, should be 403");
        }
        return mapper.memberListToDto(repository.getAllMembers());
    }
}
