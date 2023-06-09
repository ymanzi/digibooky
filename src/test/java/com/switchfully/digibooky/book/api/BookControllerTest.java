package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.service.dto.AuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    
    @Autowired
    private BookController bookController;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private MemberRepository memberRepository;
    private UUID admin;
    private UUID normal;
    private BookDto bookToRetrieve;

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
                .withINSS("122-456-789")
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

        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");
        bookToRetrieve = book1;
        BookDto book2 = new BookDto("isbn2", "title2", new Author("first2", "last2"), "1");
        BookDto book3 = new BookDto("isbn3", "title3", new Author("first3", "last3"), "1");

        bookController.create(book1, admin.toString());
        bookController.create(book2, admin.toString());
        bookController.create(book3, admin.toString());
    }

    @Test
    void createBook_givenABookToCreate_thenTheNewlyCreatedBookIsSavedAndReturned() {

        //Given
        BookDto newBook = new BookDto("isbn4", "title", new Author("first1", "last1"), "1");

        //When
        BookDto savedBook = bookController.create(newBook, admin.toString());

        //Then
        assertThat(savedBook).isEqualTo(newBook);
    }

    @Test
    void getAll_givenARepositoryWithBooks_thenRetrieveAllTheBooks() {
        //Given
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");
        BookDto book2 = new BookDto("isbn2", "title2", new Author("first2", "last2"), "1");
        BookDto book3 = new BookDto("isbn3", "title3", new Author("first3", "last3"), "1");

        //When
        List<BookDto> listOfBooks = bookController.getAll();

        //Then
        assertThat(listOfBooks).containsExactlyInAnyOrder(book1, book2, book3);
    }

    @Test
    void getByIsbn_givenARepositoryWithBooks_thenRetrieveTheBookWithTheGivenIsbn() {
        //Given
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");

        //When
        List<BookDto> book = bookController.getByIsbn("isbn1");

        //Then
        assertThat(book).containsExactly(book1);
    }

    @Test
    void getByIsbn_givenANonExistingIsbn_thenReturnAnEmptyList() {
        //Then
        assertTrue(bookController.getByIsbn("notExisting").isEmpty());
    }

    @Test
    void getByTitle_givenARepositoryWithBooks_thenRetrieveTheBookWithTheGivenTitle() {
        //Given
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");

        //When
        List<BookDto> book = bookController.getByTitle("title1");

        //Then
        assertThat(book).containsExactly(book1);
    }

    @Test
    void getByTitle_givenANonExistingIsbn_thenReturnEmptyList() {

        //Then
        assertTrue(bookController.getByTitle("notExisting").isEmpty());
    }

    @Test
    void getByAuthor_givenARepositoryWithBooks_thenRetrieveTheBookWithTheGivenAuthor() {

        //When
        List<BookDto> listOfBooks = bookController.getByAuthor(new AuthorDto("first1", "last1"));

        //Then
        assertThat(listOfBooks).containsExactly(bookToRetrieve);
    }

    @Test
    void getByAuthor_givenANonExistingAuthor_thenReturnEmptyList() {

        //Then
        assertTrue(bookController.getByAuthor(new AuthorDto("Esteban", "Veraart")).isEmpty());
    }

    @Test
    void getByAuthor_givenANullAuthor_thenThrowANoBookByAuthorException() {

        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookController.getByAuthor(null))
                .withMessage("The author can not be null");
    }

    @Test
    void deleteByTitle_givenATitleWhoDoesNotExist_thenReturnAnEmptyList()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        Book bookToDelete1 = new Book("isbnToDelete1", "titleToDelete1", new Author("firstToDelete1", "lastToDelete1"), "summary");
        bookRepository.save(bookToDelete);
        bookRepository.save(bookToDelete1);

        //When
        List<Book> booksToDelete = new ArrayList<>(List.of(bookToDelete, bookToDelete1));

        //Then
        List<Book> deletedBooks = bookRepository.delete(booksToDelete);
        assertThat(deletedBooks).containsExactlyInAnyOrder(bookToDelete1, bookToDelete);
        assertTrue(bookRepository.getByIsbn(bookToDelete.getIsbn()).isEmpty());
    }

    @Test
    void delete_givenAListOfBookToDelete_thenReturnEmptyListIfSearchForOneOfTheBooksAfterwards()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        Book bookToDelete1 = new Book("isbnToDelete1", "titleToDelete1", new Author("firstToDelete1", "lastToDelete1"), "summary");
        bookRepository.save(bookToDelete);
        bookRepository.save(bookToDelete1);

        //When
        List<Book> booksToDelete = new ArrayList<>(List.of(bookToDelete, bookToDelete1));

        //Then
        List<Book> deletedBooks = bookRepository.delete(booksToDelete);
        assertThat(deletedBooks).containsExactlyInAnyOrder(bookToDelete1, bookToDelete);
        assertTrue(bookRepository.getByIsbn(bookToDelete.getIsbn()).isEmpty());
    }

}