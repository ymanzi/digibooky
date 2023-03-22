package com.switchfully.digibooky.member.service;

import com.switchfully.digibooky.member.domain.Member;
import java.util.Collection;
import java.util.List;

public class MemberMapper {
    public MemberDto toDto(Member member){
        return new MemberDto(member.getId(), member.getFirstname(), member.getLastName(), member.getEmail(), member.getAddress(), member.getRole());
    }
    public Member fromDto(CreateMemberDto memberDto){
        Member member = new Member.MemberBuilder()
                .withINSS(memberDto.getINSS())
                .withAddress(memberDto.getAddress())
                .withEmail(memberDto.getEmail())
                .withFirstname(memberDto.getFirstname())
                .withLastName(memberDto.getLastName())
                .withRole(memberDto.getRole())
                .build();
        return member;
    }
    public List<MemberDto> memberListToDto(Collection<Member> members){
        return members.stream().map(member -> toDto(member)).toList();
    }
}
