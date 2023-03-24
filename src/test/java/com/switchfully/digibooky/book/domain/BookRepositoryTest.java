package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        assertTrue(bookRepository.getByIsbn(randomFakeIsbn).isEmpty());
    }

    @Test
    void getByTitle_givenASpecificBook_thenReturnOnlyThisSpecificBookBasedOnTitle() {
        //Given

        Book theBookIWant = new Book("111", "title1", new Author("fn1", "ln1"), "summary1");
        //When
        //Book theDesiredBook = bookRepository.getByTitle(theBookIWant.getTitle());
        List<Book> theDesiredBook = bookRepository.getByTitle("title1");

        //Then
        assertThat(theDesiredBook).containsExactly(theBookIWant);
    }

    @Test
    void getByIsbn_givenANonExistingTitle_thenReturEmptyList() {
        //Given
        String randomFakeIsbn = UUID.randomUUID().toString();

        //Then
        assertTrue(bookRepository.getByIsbn(randomFakeIsbn).isEmpty());
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
    void getByIsbn_givenANonExistingAuthor_thenReturnEmptyList() {
        //given
        Author esteban = new Author("Eseban","Veraart");

        //Then
        assertTrue(bookRepository.getByAuthor(esteban).isEmpty());
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

    @Test
    void givenAProvidedBookDto_thenUpdateMethodEditThisObjectInMap(){
        // Given
        Book bookToUpdate = new Book("isbnToUpdate", "titleToUpdate", new Author("fntu", "lntu"), "summaryToUpdate");
        bookRepository.save(bookToUpdate);

        // When
        Book bookAfterUpdate = bookRepository.update(new Book("isbnToUpdate",
                "titleUpdated",
                new Author("fnUpdated", "lnUpdated"),
                "summaryUpdated"));


        Book bookUpdated = bookRepository.getByIsbn(bookAfterUpdate.getIsbn()).get(0);

        // Then
        assertThat(bookAfterUpdate).isEqualTo(bookUpdated);
    }

    @Test
    void delete_givenABook_thenChangeDeletedAttributeOfTheBookToTrue()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        bookRepository.save(bookToDelete);
        //Then
        Book deletedBook = bookRepository.delete(bookToDelete);
        assertThat(deletedBook.isDeleted()).isTrue();
    }

    @Test
    void delete_givenADeletedBook_thenReturnEmptyListIfSearchForTheBookAfterwards()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        bookRepository.save(bookToDelete);
        bookRepository.delete(bookToDelete);

        //Then
        assertTrue(bookRepository.getByIsbn(bookToDelete.getIsbn()).isEmpty());
    }

    @Test
    void restore_givenADeletedBookAndRestored_thenReturnTheListWithTheRestoredBook()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        bookRepository.save(bookToDelete);
        bookRepository.delete(bookToDelete);
        bookRepository.delete(bookToDelete);

        //Then
        assertThat(bookRepository.getByIsbn(bookToDelete.getIsbn())).containsExactly(bookToDelete);
    }

    @Test
    void delete_givenAListOfBook_thenChangeDeletedAttributeOfThoseBooksToTrue()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        bookRepository.save(bookToDelete);

        //When
        Book deletedBook = bookRepository.delete(bookToDelete);

        //Then
        assertThat(deletedBook.isDeleted()).isTrue();
        assertThat(deletedBook).isEqualTo(bookToDelete);
    }

    @Test
    void restore_givenAListOfBook_thenChangeDeletedAttributeOfThoseBooksToFalse()
    {
        //Given
        Book bookToDelete = new Book("isbnToDelete", "titleToDelete", new Author("firstToDelete", "lastToDelete"), "summary");
        bookRepository.save(bookToDelete);

        //When
        Book deletedBook = bookRepository.delete(bookToDelete);
        bookRepository.delete(bookToDelete);

        //Then
        assertThat(deletedBook.isDeleted()).isFalse();
        assertThat(deletedBook).isEqualTo(bookToDelete);
    }

    @Test
    void delete_givenAListOfBookToDelete_thenTrowAnEmptyListAfterwards()
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
        assertTrue(bookRepository.getByIsbn(bookToDelete.getIsbn()).isEmpty());
        assertThat(deletedBooks).containsExactlyInAnyOrder(bookToDelete1, bookToDelete);
    }
}