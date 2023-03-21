package com.switchfully.digibooky.book.domain;

import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<String, Book> booksByIsbn;

    public BookRepository() {
        this.booksByIsbn = new ConcurrentHashMap<>();
    }

    public Collection<Book> getAll(){
        return booksByIsbn.values();
    }

    public Book save(Book book){
        booksByIsbn.put(book.getIsbn(), book);
        return book;
    }

    public Book getByIsbn(String isbn){
        Book foundBook = booksByIsbn.get(isbn);
        return checkIfBookExists(foundBook);
    }

    public Book getByTitle(String title){
        Book foundBook = booksByIsbn
                .values()
                .stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);
        return checkIfBookExists(foundBook);
    }

    public Book getByAuthor(Author author){
        Book foundBook = booksByIsbn
                .values()
                .stream()
                .filter(book -> book.getAuthor().equals(author))
                .findFirst()
                .orElse(null);
        return checkIfBookExists(foundBook);
    }

    public Book checkIfBookExists(Book book){
        if (book == null /*|| book.isDeleted()*/){
            //placeholder
            throw new RuntimeException("PLACEHOLDER");
        }
        return book;
    }
}
