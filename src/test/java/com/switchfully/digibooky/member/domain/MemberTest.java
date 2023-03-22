package com.switchfully.digibooky.member.domain;

import org.junit.jupiter.api.Assertions;
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
                .withFirstname("François")
                .withLastName("Pignon")
                .withEmail("francoispignon@gmail.com")
                .withAddress(address)
                .withRole(Role.ADMIN)
                .build();
    }

    @Test
    void testCreateMemberWithAllFieldsFilledIn(){
        assertNotNull(member1);
        assertEquals("123-456-789", member1.getINSS());
        assertEquals("François", member1.getFirstname());
        assertEquals("Pignon", member1.getLastName());
        assertEquals("francoispignon@gmail.com", member1.getEmail());
        assertEquals("Main Street", member1.getAddress().streetName());
        assertEquals("1150", member1.getAddress().streetNumber());
        assertEquals("12125", member1.getAddress().postalCode());
        assertEquals("New-York City", member1.getAddress().city());
        assertEquals(Role.ADMIN, member1.getRole());
    }

    @Test
    void testCreateMemberWithOnlyNeededFieldsFilledIn(){
        Member memberAda = new Member.MemberBuilder()
                .withINSS("581-673-645")
                .withEmail("adalovelace@computerscience.com")
                .withLastName("Lovelace")
                .withAddress(new Address(null, null, null, "Washington DC"))
                .build();

        assertNotNull(memberAda);
        assertNotNull(memberAda.getINSS());
        assertNotNull(memberAda.getLastName());
        assertNotNull(memberAda.getEmail());
        assertNotNull(memberAda.getAddress());
        assertNotNull(memberAda.getAddress().city());
    }

    @Test
    void test_GivenRequiredFieldINSSNotFilledIn_ThenThrowException(){
        assertThrows(INSSMissingException.class, () -> new Member.MemberBuilder()
                .withLastName("Lovelace")
                .withEmail("adalovelace@harvard.edu")
                .withAddress(new Address("", "", "", "London"))
                .build());
    }

    @Test
    void test_GivenRequiredFieldLastNameNotFilledIn_ThenThrowException(){
        assertThrows(LastNameMissingException.class, () -> new Member.MemberBuilder()
                .withINSS("672-681-346")
                .withEmail("adalovelace@harvard.edu")
                .withAddress(new Address("", "", "", "London"))
                .build());
    }

    @Test
    void test_GivenRequiredFieldEmailNotFilledIn_ThenThrowException() {
        assertThrows(EmailMissingException.class, () -> new Member.MemberBuilder()
                .withLastName("Lovelace")
                .withINSS("672-681-346")
                .withAddress(new Address("", "", "", "London"))
                .build());
    }
    @Test
    void test_GivenRequiredFieldCityInAddress_ThenThrowException(){
        assertThrows(CityInAddressMissingException.class, () -> new Member.MemberBuilder()
                .withINSS("672-681-346")
                .withLastName("Lovelace")
                .withEmail("adalovelace@harvard.edu")
                .withAddress(new Address("", "", "", ""))
                .build());
    }

    @Test
    public void testValidEmailFormat() {
        Assertions.assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withLastName("Jordan")
                .withFirstname("Michael")
                .withEmail("michaeljordan@gmail")
                .withAddress(address)
                .build());
        Assertions.assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-012")
                .withLastName("James")
                .withFirstname("Lebron")
                .withEmail("lebronjames.com")
                .withAddress(address)
                .build());
        Assertions.assertThrows(InvalidEmailFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-012")
                .withLastName("Bryant")
                .withFirstname("Kobe")
                .withEmail("kobebryant@gmail.")
                .withAddress(address)
                .build());
    }

    @Test
    public void testValidINSSFormat(){
        assertThrows(InvalidINSSFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-")
                .withLastName("Jordan")
                .withFirstname("Michael")
                .withEmail("michaeljordan@gmail.com")
                .withAddress(address)
                .build());
        assertThrows(InvalidINSSFormatException.class, () -> new Member.MemberBuilder()
                .withINSS("123-456-789-0124-15")
                .withLastName("James")
                .withFirstname("Lebron")
                .withEmail("lebronjames@gmail.com")
                .withAddress(address)
                .build());
    }
}
