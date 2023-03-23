package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.service.dto.AuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.mapper.AuthorMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private AuthorMapper authorMapper;

    @BeforeEach
    void setup(){
        BookDto book1 = new BookDto("1234567891", "book1", new Author("Esteban", "Veraart"), "a cool book" );
        BookDto book2 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );
        bookService.save(book1);
        bookService.save(book2);
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
        //then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.getByIsbn("NotExisting"))
                .withMessage("There is no book with the requested Isbn");
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
        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.getByTitle("notExisting"))
                .withMessage("There is no book with the requested title");
    }

    @Test
    void getByAuthor_givenALstOfBooks_thenBookRetrievedIsEqualToTheBook1() {
        BookDto book1 = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        List<BookDto> listOfBookRetrieved = bookService.getByAuthor(authorMapper.toDto(book1.getAuthor()));

        //then
        assertThat(listOfBookRetrieved).containsExactly(book1);
    }

    @Test
    void getByAuthor_givenALstOfBooks_thenBookRetrievedIsNotEqualToTheBook1() {
        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookService.getByAuthor(new AuthorDto("firstUnknown", "lastUnknown")))
                .withMessage("There is no book with the requested author");
    }

    @Test
    void save_givenABookToSave_thenSavedBookIsEqualToTheBookToSave() {
        BookDto bookToSave = new BookDto("9876543210", "book2", new Author("Maxime", "Rouve"), "a no cool book" );

        //when
        BookDto bookSaved = bookService.save(bookToSave);

        //then
        assertThat(bookToSave).isEqualTo(bookSaved);
    }

    @Test
    void changeStringWithAsteriskToRegex_givenStringStartingWithAsterisk_thenReturnTheRegexString(){
        //Given
        String regexString = "(^|.+)le1";

        //then
        assertThat(bookService.changeStringWithAsteriskToRegex("*le1")).isEqualTo(regexString);
    }
}