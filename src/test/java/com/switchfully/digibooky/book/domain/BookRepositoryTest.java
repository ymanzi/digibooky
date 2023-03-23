package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRepositoryTest {

    private BookRepository bookRepository = new BookRepository();

    @Autowired
    private BookService bookService;

    @BeforeEach
    void setup() {
        Book book1 = new Book("111", "title1", new Author("fn1", "ln1"), "summary1");
        Book book2 = new Book("222", "title2", new Author("fn2", "ln2"), "summary2");
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    void save_givenABookToSave_thenSavedMovieIsEqualToTheBookToSave() {
        //Given
        bookRepository = new BookRepository();
        Book bookToSave = new Book("1234",
                "testTitle",
                new Author("firstNameAuthor", "lastNameAuthor"),
                "testSummary blablaablaa");

        //When
        Book savedBook = bookRepository.save(bookToSave);

        //Then
        assertThat(bookToSave).isEqualTo(savedBook);
    }

    @Test
    void getAll_givenANonEmptyMapOfBooks_thenReturnAllTheSavedBooksInMap() {
        //Given
        Book book1 = new Book("111", "title1", new Author("fn1", "ln1"), "summary1");
        Book book2 = new Book("222", "title2", new Author("fn2", "ln2"), "summary2");

        //When
        Collection<Book> allSavedBooks = bookRepository.getAll();

        //Then
        assertThat(allSavedBooks).containsExactlyInAnyOrder(book1, book2);
    }

    @Test
    void getByIsbn_givenASpecificBook_thenReturnOnlyThisSpecificBookBasedOnIsbn() {
        //Given
        Book theBookIWant = new Book("111", "title1", new Author("fn1", "ln1"), "summary1");
        //When
        //Book theDesiredBook = bookRepository.getByIsbn(theBookIWant.getIsbn());
        List<Book> theDesiredBook = bookRepository.getByIsbn("1");
        //Then
        assertThat(theDesiredBook).containsExactly(theBookIWant);
    }

    @Test
    void getByIsbn_givenANonExistingIsbn_thenReturnNull() {
        //Given
        String randomFakeIsbn = UUID.randomUUID().toString();

        //Then
        org.assertj.core.api.Assertions
                .assertThatRuntimeException()
                .isThrownBy(() -> bookRepository.getByIsbn(randomFakeIsbn))
                .withMessage("There is no book with the requested Isbn");

    }

    @Test
    void getByTitle_givenASpecificBook_thenReturnOnlyThisSpecificBookBasedOnTitle() {
        //Given

        Book theBookIWant = new Book("111", "t*le1", new Author("fn1", "ln1"), "summary1");
        //When
        //Book theDesiredBook = bookRepository.getByTitle(theBookIWant.getTitle());
        List<Book> theDesiredBook = bookRepository.getByTitle("title1");

        //Then
        assertThat(theDesiredBook).containsExactly(theBookIWant);
    }

    @Test
    void getByIsbn_givenANonExistingTitle_thenReturnNull() {
        //Given
        String randomFakeIsbn = UUID.randomUUID().toString();

        //Then
        org.assertj.core.api.Assertions
                .assertThatRuntimeException()
                .isThrownBy(() -> bookRepository.getByTitle(randomFakeIsbn))
                .withMessage("There is no book with the requested title");

    }

    @Test
    void getByAuthor() {
        //Given
        Author author1 = new Author("fn1", "ln1");
        Book theBookIWant = new Book("111", "title1", author1, "summary1");

        //When
        List<Book> theDesiredBook = bookRepository.getByTitle(theBookIWant.getTitle());

        //Then
        assertThat(theDesiredBook).containsExactly(theBookIWant);
    }

    @Test
    void getByIsbn_givenANonExistingAuthor_thenReturnNull() {
        //Given
        String randomFakeIsbn = UUID.randomUUID().toString();

        //Then
        org.assertj.core.api.Assertions
                .assertThatRuntimeException()
                .isThrownBy(() -> bookRepository.getByAuthor(new Author(randomFakeIsbn, randomFakeIsbn)))
                .withMessage("There is no book with the requested author");

    }


    @Test
    void checkIfBookExists_givenNoBookProvided_thenThrowException() {
        //Given
        BookRepository bookRepository = new BookRepository();

        //Then
        Assertions.assertThatRuntimeException()
                .isThrownBy(() -> bookRepository.checkIfBookExists(null, "isbn"))
                .withMessage("There is no book with the requested Isbn");
    }

    @Test
    void checkWildCard_givenATitleStringMatchingARegexString_thenReturnTrue(){
        //Given
        String title = "title1";
        String regexTitle = bookService.changeStringWithAsteriskToRegex("t*le1");

        //Then
        assertThat(bookRepository.checkWildcard(title, regexTitle)).isTrue();
    }

    @Test
    void checkWildCard_givenATitleStringMatchingAStartingRegexString_thenReturnTrue(){
        //Given
        String title = "title1";
        String regexTitle = bookService.changeStringWithAsteriskToRegex("*le1");

        //Then
        assertThat(bookRepository.checkWildcard(title, regexTitle)).isTrue();
    }

    @Test
    void checkWildCard_givenATitleStringMatchingAEndingRegexString_thenReturnTrue(){
        //Given
        String title = "title1";
        String regexTitle = bookService.changeStringWithAsteriskToRegex("tit*");

        //Then
        assertThat(bookRepository.checkWildcard(title, regexTitle)).isTrue();
    }

    @Test
    void checkWildCard_givenATitleStringIsNotMatchingARegexString_thenReturnFalse(){
        //Given
        String title = "title1";
        String regexTitle = bookService.changeStringWithAsteriskToRegex("t*a1");

        //then
        assertThat(bookRepository.checkWildcard(title, regexTitle)).isFalse();
    }

}