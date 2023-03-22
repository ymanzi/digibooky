package com.switchfully.digibooky.member.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    private Member member1;
    private Address address;

    @BeforeEach
    void setup(){
        address = new Address("Main Street", "1150", "12125", "New-York City");
        member1 = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstName("François")
                .withLastName("Pignon")
                .withEmail("francoispignon@gmail.com")
                .withAddress(address)
                .build();
    }

    @Test
    void testCreateMemberWithAllFieldsFilledIn(){
        assertNotNull(member1);
        assertEquals("123-456-789", member1.getINSS());
        assertEquals("François", member1.getFirstName());
        assertEquals("Pignon", member1.getLastName());
        assertEquals("francoispignon@gmail.com", member1.getEmail());
        assertEquals("Main Street", member1.getAddress().getStreet());
        assertEquals("1150", member1.getAddress().getNumber());
        assertEquals("12125", member1.getAddress().getZipCode());
        assertEquals("New-York City", member1.getAddress().getCity());
    }

    @Test
    void testCreateMemberWithOnlyNeededFieldsFilledIn(){
        Member memberAda = new Member.MemberBuilder()
                .withLastName("Lovelace")
                .withAddress(new Address(null, null, null, "Washington DC"))
                .build();

        assertNotNull(memberAda);
        assertNotNull(memberAda.getAddress());
        assertNotNull(memberAda.getAddress().getCity());
        assertNull(memberAda.getINSS());
        assertNull(memberAda.getFirstName());
        assertNull(memberAda.getINSS());
        assertNull(memberAda.getEmail());
        assertNull(memberAda.getAddress().getStreet());
        assertNull(memberAda.getAddress().getNumber());
        assertNull(memberAda.getAddress().getZipCode());
    }

    @Test
    void test_GivenRequiredFieldNotFilledIn_ThenThrowException(){
        assertThrows(MissingLastNameFieldException.class, () -> new Member("123-456-789", "Ada", "", "johndoe@example.com", new Address("", "", "", "Princetown")));

    }

    @Test
    void test_GivenRequiredFieldCityInAddress_ThenThrowException(){
        assertThrows(MissingCityFieldException.class, () -> new Member("123-456-789", "", "Lovelace", "janedoe@example.com", new Address("", "", "", "")));
    }

    @Test
    public void testValidEmailFormat() {
        assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withLastName("Jordan")
                .withFirstName("Michael")
                .withEmail("michaeljordan@gmail")
                .withAddress(address)
                .build());
        assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-012")
                .withLastName("James")
                .withFirstName("Lebron")
                .withEmail("lebronjames.com")
                .withAddress(address)
                .build());
        assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-0124-15")
                .withLastName("Bryant")
                .withFirstName("Kobe")
                .withEmail("kobebryant@gmail.")
                .withAddress(address)
                .build());
    }

    @Test
    public void testValidINSSFormat(){
        assertThrows(InvalidINSSFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-")
                .withLastName("Jordan")
                .withFirstName("Michael")
                .withEmail("michaeljordan@gmail.com")
                .withAddress(address)
                .build());
        assertThrows(InvalidINSSFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-0124-15")
                .withLastName("James")
                .withFirstName("Lebron")
                .withEmail("lebronjames@gmail.com")
                .withAddress(address)
                .build());
    }
}
