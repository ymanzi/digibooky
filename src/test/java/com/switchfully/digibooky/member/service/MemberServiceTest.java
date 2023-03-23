package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import com.switchfully.digibooky.rental.service.exceptions.NoRightException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberMapper memberMapper;
    private MemberService memberService;
    private Member member1;
    private Member member2;
    private CreateMemberDto createMemberDto;

    @BeforeEach
    void setup(){
        memberRepository = new MemberRepository();
        memberMapper = new MemberMapper();
        memberService = new MemberService(memberRepository);
        member1 = new Member.MemberBuilder()
                .withINSS("111-111-111")
                .withFirstname("Denzel")
                .withLastName("Washington")
                .withEmail("denzelwashington@equalizer.com")
                .withAddress(new Address("Universal Studio street", "5", "90027", "Holliwood"))
                .withRole(Role.MEMBER)
                .build();
        member2 = new Member.MemberBuilder()
                .withINSS("222-222-222")
                .withFirstname("Jenifer")
                .withLastName("Aniston")
                .withEmail("jeniferaniston@friends.io")
                .withAddress(new Address("Bedford street", "90", "10014", "New York"))
                .withRole(Role.ADMIN)
                .build();
        createMemberDto = new CreateMemberDto("333-333-333", "Brad", "Pitt", "bradpitt@smith.com", new Address("", "", "", "San Francisco"));

        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    @Test
    void getAllMembers_givenAdminId_shouldReturnAllMembers(){

        String adminId = member2.getId().toString();
        List<MemberDto> result = memberService.getAllMembers(adminId);

        assertEquals(3, result.size());
        assertTrue(result.stream().anyMatch(dto -> dto.getEmail().equals(member1.getEmail())));
        assertTrue(result.stream().anyMatch(dto -> dto.getEmail().equals(member2.getEmail())));
    }

    @Test
    void getAllMembers_givenANonAdminID_shouldThrowException(){
        String nonAdminId = member1.getId().toString();
        assertThrows(NoRightException.class, () -> memberService.getAllMembers(nonAdminId));
    }

    @Test
    void registerMember_givenValidCreateMemberDto_shouldReturnMemberDto(){
        MemberDto memberDto = memberService.registerMember(createMemberDto);
        assertNotNull(memberDto.getId());
        assertEquals(createMemberDto.getLastName(), memberDto.getLastName());
        assertEquals(createMemberDto.getEmail(), memberDto.getEmail());
        assertEquals(createMemberDto.getAddress(), memberDto.getAddress());
        assertEquals(Role.MEMBER, memberDto.getRole());
    }

    @Test
    void registerLibrarian_givenValidAdminIDAndCreateDto_shouldReturnMemberDTO(){
        String adminID = member2.getId().toString();
        MemberDto memberDto = memberService.registerLibrarian(createMemberDto, adminID);
        assertNotNull(memberDto.getId());
        assertEquals(createMemberDto.getLastName(), memberDto.getLastName());
        assertEquals(createMemberDto.getEmail(), memberDto.getEmail());
        assertEquals(Role.LIBRARIAN, memberDto.getRole());
    }

    @Test
    void registerLibrarian_givenNonValidAdminIDAndValidCreateDto_shouldThrowException(){
        String nonAdminId = member1.getId().toString();
        assertThrows(NoRightException.class, () -> memberService.registerLibrarian(createMemberDto, nonAdminId));
    }
}
