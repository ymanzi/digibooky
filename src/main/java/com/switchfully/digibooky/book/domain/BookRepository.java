package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.BookWithThisIsbnAlreadyExist;
import com.switchfully.digibooky.book.exceptions.CanNotUpdateNonExistingBook;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<String, Book> booksByIsbn;

    public BookRepository() {
        this.booksByIsbn = new ConcurrentHashMap<>();
    }

    public Collection<Book> getAll(){
        return booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public Book save(Book book){
        throwErrorIfBookExists(book);
        Book savedBook = booksByIsbn.put(book.getIsbn(), book);
        return book;
    }

    public List<Book> getByIsbn(String isbn){
        List<Book> listOfFoundBooks = booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> checkWildcard(book.getIsbn(), isbn))
                .collect(Collectors.toList());
        return listOfFoundBooks;
    }

    public List<Book> getByTitle(String title){
        return  booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> checkWildcard(book.getTitle(), title))
                .collect(Collectors.toList());
    }

    public List<Book> getByAuthor(Author author){
        return booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> authorIsEqual(author, book))
                .collect(Collectors.toList());
    }

    private boolean authorIsEqual(Author author, Book book) {
        if (!author.getFirstname().isBlank() && !author.getLastname().isBlank()) {
            return checkWildcard(book.getAuthor().getFirstname(), author.getFirstname()) && checkWildcard(book.getAuthor().getLastname(), author.getLastname());
        }
        if (author.getFirstname().isBlank() && !author.getLastname().isBlank()) {
            return checkWildcard(book.getAuthor().getLastname(), author.getLastname());
        }
        if (!author.getFirstname().isBlank() && author.getLastname().isBlank()) {
            return checkWildcard(book.getAuthor().getFirstname(), author.getFirstname());
        }
        return false;
    }

    public Book update(Book updatedBook){
        if (!checkIfBookExist(updatedBook)) {
            throw new CanNotUpdateNonExistingBook();
        }
        booksByIsbn.put(updatedBook.getIsbn(), updatedBook);
        return updatedBook;
    }

    public Book delete(Book bookToDelete){
        bookToDelete.toggleDeleted();
        return update(bookToDelete);
    }

    public List<Book> delete(List<Book> listOfBooks){
        return listOfBooks
                .stream()
                .map(this::delete)
                .toList();
    }

    public void throwErrorIfBookExists(Book book){
        if (checkIfBookExist(book)) {
            throw new BookWithThisIsbnAlreadyExist();
        }
    }
    
    public boolean checkIfBookExist(Book book){
        return booksByIsbn.containsKey(book.getIsbn());
    }

    public boolean checkWildcard(String stringToCheck, String regexString){
        Pattern pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(stringToCheck).find();
    }
}
