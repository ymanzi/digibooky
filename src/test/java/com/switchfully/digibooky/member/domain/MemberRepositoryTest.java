package com.switchfully.digibooky.member.domain;

import com.switchfully.digibooky.member.domain.exceptions.EmailAlreadyExistsException;
import com.switchfully.digibooky.member.domain.exceptions.INSSAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class MemberRepositoryTest {

    MemberRepository memberRepository = new MemberRepository();
    Member member;

    @BeforeEach
    void setup(){
        memberRepository = new MemberRepository();
        member = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("Bill")
                .withLastName("Gates")
                .withEmail("bill@microsoft.com")
                .withAddress(new Address("", "", "", "Seattle"))
                .build();
        memberRepository.save(member);
    }

    @Test
    void Save_WhenAMemberIsSaved_ThenRepoContainsThisMember(){
        assertTrue(memberRepository.getAllMembers().contains(member));
    }

    @Test
    void checkIfUniqueEmail_WhenASecondMemberWithSameEmailIsCreated_ThenThrowsException(){
        Member member2 = new Member.MemberBuilder()
                .withINSS("528-437-615")
                .withLastName("Turner")
                .withEmail("bill@microsoft.com")
                .withAddress(new Address("", "", "", "Mexico"))
                .build();
        assertThrows(EmailAlreadyExistsException.class, () -> memberRepository.save(member2));
    }


    @Test
    void checkIfUniqueINSS_WhenASecondMemberWithSameINSSIsCreated_ThenThrowsException() {
        Member member2 = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withEmail("bill@apple.com")
                .withLastName("Turner")
                .withAddress(new Address("", "", "", "Mexico"))
                .build();
        assertThrows(INSSAlreadyExistsException.class, () -> memberRepository.save(member2));
    }

    @Test
    void testGetAllMembers(){
        Member member2 = new Member.MemberBuilder()
                .withINSS("951-753-852-564")
                .withEmail("abc@abc.abc")
                .withLastName("Smith")
                .withAddress(new Address("", "", "", "Mexico"))
                .build();
        memberRepository.save(member2);
        Collection<Member> allMembers = memberRepository.getAllMembers();
        assertEquals(3, allMembers.size());
        assertTrue(allMembers.contains(member));
        assertTrue(allMembers.contains(member2));
    }

    @Test
    void testGetMemberByID_WhenAnIdIsProvided_ThenReturnTheMember(){
        UUID Id = member.getId();
        assertEquals(member, memberRepository.getMemberById(Id));
    }
}
