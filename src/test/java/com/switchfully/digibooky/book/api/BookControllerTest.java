package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.service.dto.AuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {

    @Autowired
    private BookController bookController;

    @BeforeEach
    void setup(){
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");
        BookDto book2 = new BookDto("isbn2", "title2", new Author("first2", "last2"), "1");
        BookDto book3 = new BookDto("isbn3", "title3", new Author("first3", "last3"), "1");

        bookController.create(book1);
        bookController.create(book2);
        bookController.create(book3);
    }

    @Test
    void createBook_givenABookToCreate_thenTheNewlyCreatedBookIsSavedAndReturned() {

        //Given
        BookDto newBook = new BookDto("isbn4", "title", new Author("first1", "last1"), "1");

        //When
        BookDto savedBook = bookController.create(newBook);

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
        BookDto book = bookController.getByIsbn("isbn1");

        //Then
        assertThat(book).isEqualTo(book1);
    }

    @Test
    void getByIsbn_givenANonExistingIsbn_thenThrowANoBookByIsbnException() {
        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookController.getByIsbn("notExisting"))
                .withMessage("There is no book with the requested Isbn");
    }

    @Test
    void getByTitle_givenARepositoryWithBooks_thenRetrieveTheBookWithTheGivenTitle() {
        //Given
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");

        //When
        BookDto book = bookController.getByTitle("title1");

        //Then
        assertThat(book).isEqualTo(book1);
    }

    @Test
    void getByTitle_givenANonExistingIsbn_thenThrowANoBookByTitleException() {

        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookController.getByTitle("notExisting"))
                .withMessage("There is no book with the requested title");
    }

    @Test
    void getByAuthor_givenARepositoryWithBooks_thenRetrieveTheBookWithTheGivenAuthor() {
        //Given
        BookDto book1 = new BookDto("isbn1", "title1", new Author("first1", "last1"), "1");

        //When
        BookDto book = bookController.getByAuthor(new AuthorDto("first1", "last1"));

        //Then
        assertThat(book).isEqualTo(book1);
    }

    @Test
    void getByAuthor_givenANonExistingAuthor_thenThrowANoBookByAuthorException() {

        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookController.getByAuthor(new AuthorDto("firstUnknown", "lastUnknown")))
                .withMessage("There is no book with the requested author");
    }

    @Test
    void getByAuthor_givenANullAuthor_thenThrowANoBookByAuthorException() {

        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookController.getByAuthor(null))
                .withMessage("There is no book with the requested author");
    }
}