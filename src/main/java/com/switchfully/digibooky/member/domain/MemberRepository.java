package com.switchfully.digibooky.member.domain;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemberRepository {
    private final Map<UUID, Member> memberByUUIDRepository;

    public MemberRepository() {
        memberByUUIDRepository = new ConcurrentHashMap<>();
        Member admin = new Member.MemberBuilder()
                .withEmail("admin@admin.com")
                .withAddress(new Address("a", "a", "a", "a"))
                .withFirstname("admin")
                .withLastName("admin")
                .withINSS("000-000-000")
                .withRole(Role.ADMIN)
                .build();
        memberByUUIDRepository.put(admin.getId(), admin);
    }
    public Member save(Member newMember){
        checkIfUniqueINSS(newMember);
        checkIfUniqueEmail(newMember);
        memberByUUIDRepository.put(newMember.getId(),newMember);
        return newMember;
    }

    private void checkIfUniqueEmail(Member newMember) {
        Member validateNotPresent= memberByUUIDRepository.values()
                .stream()
                .filter(member -> member.getEmail().equals(newMember.getEmail()))
                .findFirst()
                .orElse(null);
        if (validateNotPresent != null)
            throw new RuntimeException("placeholder");
    }

    private void checkIfUniqueINSS (Member newMember) {
        Member validateNotPresent = memberByUUIDRepository.values()
                .stream()
                .filter(member -> member.getINSS().equals(newMember.getINSS()))
                .findFirst()
                .orElse(null);
        if (validateNotPresent != null)
            throw new RuntimeException("placeholder");
    }
    public Collection<Member> getAllMembers(){
        return memberByUUIDRepository.values();
    }
    public Member getMemberById(UUID id){
        return memberByUUIDRepository.get(id);
    }

    public String getAdminId() {
         return memberByUUIDRepository.values().stream().filter(member -> member.getRole().equals(Role.ADMIN)).findFirst().get().getId().toString();
    }
}
