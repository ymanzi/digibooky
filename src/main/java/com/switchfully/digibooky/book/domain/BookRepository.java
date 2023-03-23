package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoBookByAuthorException;
import com.switchfully.digibooky.book.exceptions.NoBookByIsbnException;
import com.switchfully.digibooky.book.exceptions.NoBookByTitleException;
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
        booksByIsbn.put(book.getIsbn(), book);
        return book;
    }

    public List<Book> getByIsbn(String isbn){
        List<Book> listOfFoundBooks = booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> checkWildcard(book.getIsbn(), isbn))
                .collect(Collectors.toList());

        return checkIfBookExists(listOfFoundBooks, "isbn");
    }

    public List<Book> getByTitle(String title){
        List<Book> listOfFoundBooks = booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> checkWildcard(book.getTitle(), title))
                .collect(Collectors.toList());

        return checkIfBookExists(listOfFoundBooks, "title");
    }

    public List<Book> getByAuthor(String authorId){
        List<Book> listOfFoundBooks = booksByIsbn
                .values()
                .stream()
                .filter(book -> !book.isDeleted())
                .filter(book -> String.valueOf(book.getAuthor().getUserId()).equals(authorId) )
                .collect(Collectors.toList());

        return checkIfBookExists(listOfFoundBooks, "author");
    }


    public List<Book> checkIfBookExists(List<Book> listOfBooks, String typeOfException){
        if (listOfBooks == null /*|| listOfBooks.isDeleted()*/){
            switch(typeOfException){
                case "isbn" -> throw new NoBookByIsbnException();
                case "title" -> throw new NoBookByTitleException();
                case "author" -> throw new NoBookByAuthorException();
            }
        }
        return listOfBooks;
    }

    public boolean checkWildcard(String stringToCheck, String regexString){
        Pattern pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(stringToCheck).find();
    }
}
