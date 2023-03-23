package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.member.service.MemberMapper;
import com.switchfully.digibooky.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;

public class MemberServiceTest {

    private MemberRepository memberRepository;
    private MemberMapper memberMapper;
    private MemberService memberService;
    private Member member1;
    private Member member2;

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
                .withRole(Role.MEMBER)
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
    // continue to implement
}
