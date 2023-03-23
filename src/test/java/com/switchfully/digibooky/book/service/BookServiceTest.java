package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.mapper.AuthorMapper;
import com.switchfully.digibooky.member.domain.Address;
import com.switchfully.digibooky.member.domain.Member;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;
import java.util.UUID;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private MemberRepository memberRepository;
    private UUID admin;
    private UUID normal;
    private int userId;

    @BeforeEach
    void setup(){
        Address address = new Address("Main Street", "1150", "12125", "New-York City");
        Member memberAdmin = new Member.MemberBuilder()
                .withINSS("123-456-789")
                .withFirstname("François")
                .withLastName("Pignon")
                .withEmail("francoispignon@gmail.com")
                .withAddress(address)
                .withRole(Role.ADMIN)
                .build();
        Address address1 = new Address("Master Street", "4550", "34566", "Mannathan");
        Member memberNormal = new Member.MemberBuilder()
                .withINSS("987-654-321")
                .withFirstname("Esteban")
                .withLastName("Veraart")
                .withEmail("Estebann@gmail.com")
                .withAddress(address1)
                .withRole(Role.MEMBER)
                .build();
        admin = memberAdmin.getId();
        normal = memberNormal.getId();
        memberRepository.save(memberAdmin);
        memberRepository.save(memberNormal);
        BookDto book1 = new BookDto("1234567891", "book1", new Author("Esteban", "Veraart"), "a cool book" );
        BookDto book2 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );
        userId = book2.getAuthor().getUserId();
        bookService.save(book1, memberAdmin.getId().toString());
        bookService.save(book2, memberAdmin.getId().toString());

    }

    @Test
    void getAll_givenAListOfBooks_thenListOfBookIsEqualToTheListOfBookDto() {
        //given
                BookDto book1 = new BookDto("1234567891", "book1", new Author("Esteban", "Veraart"), "a cool book" );
                BookDto book2 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );


        //when
        List<BookDto> listOfBooks = bookService.getAll();

        //then
        assertThat(listOfBooks).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void getByIsbn_givenAListOfBooks_thenBookRetrievedIsEqualToTheBook1() {
        //given
        BookDto book1 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        List<BookDto> listOfBooksRetrieved = bookService.getByIsbn(book1.getIsbn());

        //then
        assertThat(listOfBooksRetrieved).containsExactlyInAnyOrder(book1);
    }

    @Test
    void getByIsbn_givenAListOfBooks_thenBookRetrievedIsNotEqualToTheBook1() {
        //given
        String randomFakeIsbn = UUID.randomUUID().toString();
        //then
        assertTrue(bookService.getByIsbn(randomFakeIsbn).isEmpty());
    }

    @Test
    void getByTitle_givenAListOfBooks_thenBookRetrievedIsEqualToTheBook1() {
        //given
        BookDto book1 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        List<BookDto> listOfBooksRetrieved = bookService.getByTitle(book1.getTitle());

        //then
        assertThat(listOfBooksRetrieved).containsExactly(book1);
    }

    @Test
    void getByTitle_givenAListOfBooks_thenBookRetrievedIsNotEqualToTheBook1() {
        //given
        String fakeTitle = "fake one";
        //then
        assertTrue(bookService.getByTitle(fakeTitle).isEmpty());
    }

    @Test
    void getByAuthor_givenALstOfBooks_thenBookRetrievedIsEqualToTheBook1() {
        BookDto book1 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        List<BookDto> listOfBookRetrieved = bookService.getByAuthor(String.valueOf(userId));

        //then
        assertThat(listOfBookRetrieved).containsExactly(book1);
    }

    @Test
    void getByAuthor_givenAnUnknownAuthorId_thenRetrieveAnEmptyList() {
        //then
        assertTrue(bookService.getByAuthor("35").isEmpty());
    }

    @Test
    void save_givenABookToSave_thenSavedBookIsEqualToTheBookToSaveWithAnAdminMember() {
        BookDto bookToSave = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        BookDto bookSaved = bookService.save(bookToSave, admin.toString());

        //then
        assertThat(bookToSave).isEqualTo(bookSaved);
    }

    @Test
    void save_givenABookToSave_thenMemberHaveNotTheAuthorization(){
        //given
        BookDto bookToSave = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.save(bookToSave, normal.toString()))
                .withMessage("Unauthorized End Point!");
    }
    @Test
    void changeStringWithAsteriskToRegex_givenStringStartingWithAsterisk_thenReturnTheRegexString(){
        //Given
        String regexString = "(^|.+)le1";

        //then
        assertThat(bookService.changeStringWithAsteriskToRegex("*le1")).isEqualTo(regexString);
    }
    @Test
    void getRole_givenAWrongMemberId_thenRetrievedNotTheMemberWithHisId(){
        //given
        String falseIdMember = "abcdefg";
        //when
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.getRoleOfMemberById(falseIdMember))
                .withMessage("Invalid UUID string: " + falseIdMember);
    }
    @Test
    void delete_givenABookDtoToDelete_thenDeleteTheBookGetById_withANormalMemberId(){
        //given
        BookDto bookToDelete = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );
        //when
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.delete(bookToDelete.getIsbn(), normal.toString(),"isbn"))
                .withMessage("Unauthorized End Point!");
    }

    @Test
    void update_givenABookToUpdate_thenUpdateTheBookGetById_withAnAdminMember(){
        //given
        BookDto bookToUpdate = new BookDto("9876543210","book3", new Author("Yves", "Manzi"), "an update book" );
        //when
        BookDto bookUpdated = bookService.update(bookToUpdate,admin.toString());
        //then
        assertThat(bookUpdated).isEqualTo(bookToUpdate);
    }

    @Test
    void update_givenABookToUpdate_thenUpdateThisBook_withANormalMember(){
        //given
        BookDto bookToUpdate = new BookDto("9876543210","book3", new Author("Yves", "Rouve"), "an update book" );
        //when
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.update(bookToUpdate, normal.toString()))
                .withMessage("Unauthorized End Point!");
    }

}