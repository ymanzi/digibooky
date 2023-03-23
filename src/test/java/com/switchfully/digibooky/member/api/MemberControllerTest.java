package com.switchfully.digibooky.member.api;

import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.service.dtos.CreateMemberDto;
import com.switchfully.digibooky.member.service.dtos.MemberDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import static io.restassured.http.ContentType.JSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MemberControllerTest {

    @Value("${server.port}")
    private int port;
    private CreateMemberDto createMemberDto;

    @BeforeEach
    void setup(){
        createMemberDto = new CreateMemberDto(
                "842-351-941"
                , "Georges"
                , "Clooney"
                , "georgesclooney@nespresso.whatelse"
                , new Address("Main street", "1000", "50000","Philips"));
    }

    @Test
    void registerMember_givenAMemberToCreate_thenTheNewMemberIsCreatedAndReturned(){
        MemberDto memberDto =
                RestAssured
                        .given()
                        .body(createMemberDto)
                        .accept(JSON)
                        .contentType(JSON)
                        .when()
                        .port(port)
                        .post("/members/register")
                        .then()
                        .assertThat()
                        .statusCode(HttpStatus.CREATED.value())
                        .extract()
                        .as(MemberDto.class);

        assertNotNull(memberDto.getId());
        assertEquals(memberDto.getLastName(), createMemberDto.getLastName());
        assertEquals(memberDto.getEmail(), createMemberDto.getEmail());
        assertEquals(memberDto.getAddress(), createMemberDto.getAddress());
    }
}
