package com.switchfully.digibooky.book.domain;

import com.switchfully.digibooky.book.exceptions.NoIsbnForBookException;
import com.switchfully.digibooky.book.exceptions.NoLastnameForAuthorException;
import com.switchfully.digibooky.book.exceptions.NoTitleForBookException;

import java.util.Objects;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
    private String summary;
    private Boolean deleted = false;
    private int amountInStore;

    public Book(String isbn, String title, Author author, String summary) {
        if (isbn == null || isbn.isEmpty() || isbn.isBlank())
            throw new NoIsbnForBookException();
        if (title == null || title.isEmpty() || title.isBlank())
            throw new NoTitleForBookException();
        if (author == null || author.getLastname() == null || author.getLastname().isEmpty() || author.getLastname().isBlank())
            throw new NoLastnameForAuthorException();

        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        amountInStore = 1;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }

    public String getSummary() {
        return summary;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    public int getAmountInStore() {
        return amountInStore;
    }
    public void decreaseAmountInStore(int amount){
        amountInStore-= amount;
    }
    public void  increaseAmountInStore(int amount){
        amountInStore += amount;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void toggleDeleted(){
        this.deleted = !deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(summary, book.summary) && Objects.equals(deleted, book.deleted);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, summary, deleted);
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author=" + author +
                ", summary='" + summary + '\'' +
                '}';
    }
}
