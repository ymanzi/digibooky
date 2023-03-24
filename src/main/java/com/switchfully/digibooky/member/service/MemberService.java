package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
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
            throw new NoRightException();
        }
        return mapper.memberListToDto(repository.getAllMembers());
    }

    public MemberDto registerMember(CreateMemberDto memberDto) {
        Member member = repository.save(mapper.fromDto(memberDto, Role.MEMBER));
        return mapper.toDto(member);
    }

    public MemberDto registerLibrarian(CreateMemberDto memberDto, String id) {
        if (repository.getMemberById(UUID.fromString(id)).getRole() != Role.ADMIN){
            throw new NoRightException();
        }
        return mapper.toDto(repository.save(mapper.fromDto(memberDto, Role.LIBRARIAN)));
    }

    public String getAdminId() {
        return repository.getAdminId();
    }
}
