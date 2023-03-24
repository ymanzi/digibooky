package com.switchfully.digibooky.member.service.dtos;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Role;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberDtoTest {

    @Test
    public void testConstructorAndGetters() {
        UUID id = UUID.randomUUID();
        Address address = new Address("Grand Place", "1", "1000", "Bruxelles");
        MemberDto dto = new MemberDto(id, "Georges", "Remi", "tintinmilou@herge.be", address, Role.MEMBER);

        assertEquals(id, dto.getId());
        assertEquals("Georges", dto.getFirstname());
        assertEquals("Remi", dto.getLastName());
        assertEquals("tintinmilou@herge.be", dto.getEmail());
        assertEquals(address, dto.getAddress());
        assertEquals(Role.MEMBER, dto.getRole());
    }
}
