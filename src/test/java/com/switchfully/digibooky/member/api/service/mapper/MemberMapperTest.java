package com.switchfully.digibooky.member.api.service.mapper;

import com.switchfully.digibooky.member.domain.*;
import com.switchfully.digibooky.member.service.*;
import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MemberMapperTest {

    private Address address;
    private Address address2;
    private Member member;
    private Member member2;

    @BeforeEach
    void setup(){
        address = new Address("Main Street", "2540", "94302", "Palo Alto");
        address2 = new Address("Grand Place", "1", "1000", "Bruxelles");
        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("Albert")
                .withLastName("Einstein")
                .withEmail("alberteinstein@princetown.edu")
                .withAddress(address)
                .withRole(Role.ADMIN)
                .build();
        member2 = new Member.MemberBuilder()
                .withINSS("987-654-321")
                .withFirstname("Georges")
                .withLastName("Remi")
                .withEmail("tintinmilou@herge.be")
                .withAddress(address2)
                .withRole(Role.MEMBER)
                .build();
    }

    @Test
    void test_toDto(){
        MemberDto dto = new MemberMapper().toDto(member);

        assertEquals(member.getId(), dto.getId());
        assertEquals("Albert", dto.getFirstname());
        assertEquals("Einstein", dto.getLastName());
        assertEquals("alberteinstein@princetown.edu", dto.getEmail());
        assertEquals(address, dto.getAddress());
        assertEquals(Role.ADMIN, dto.getRole());
    }
    @Test
    void test_fromDto(){
        CreateMemberDto createMemberDto = new CreateMemberDto(member.getINSS(), member.getFirstname(), member.getLastName(), member.getEmail(), member.getAddress());
        Member member2 = new MemberMapper().fromDto(createMemberDto, Role.MEMBER);

        assertNotNull(member2.getId());
        assertEquals("123-456-789", member2.getINSS());
        assertEquals("Albert", member2.getFirstname());
        assertEquals("Einstein", member2.getLastName());
        assertEquals("alberteinstein@princetown.edu", member2.getEmail());
        assertEquals(address, member2.getAddress());
        assertEquals(Role.MEMBER, member2.getRole());
    }

    @Test
    void test_memberListToDto(){
        MemberRepository memberRepository = new MemberRepository();
        memberRepository.save(member);
        memberRepository.save(member2);
        List<MemberDto> dtos = new MemberMapper().memberListToDto(memberRepository.getAllMembers());

        assertEquals(3, dtos.size());
        assertTrue(dtos.contains(new MemberMapper().toDto(member)));
        assertTrue(dtos.contains(new MemberMapper().toDto(member2)));
    }
}
