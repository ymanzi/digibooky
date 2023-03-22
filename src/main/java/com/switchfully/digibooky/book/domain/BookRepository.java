package com.switchfully.digibooky.book.domain;

import org.springframework.stereotype.Repository;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BookRepository {
    private final ConcurrentHashMap<String, Book> booksByIsbnRepository;

    public BookRepository() {
        this.booksByIsbnRepository = new ConcurrentHashMap<>();
    }

    public Collection<Book> getAll(){
        return booksByIsbnRepository.values();
    }

    public Book save(Book book){
        return booksByIsbnRepository.put(book.getIsbn(), book);
    }

    public Book getByIsbn(String isbn){
        Book foundBook = booksByIsbnRepository.get(isbn);
        return checkIfBookExists(foundBook);
    }

    public Book getByTitle(String title){
        Book foundBook = booksByIsbnRepository
                .values()
                .stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .orElse(null);
        return checkIfBookExists(foundBook);
    }

    public Book getByAuthor(Author author){
        Book foundBook = booksByIsbnRepository
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
