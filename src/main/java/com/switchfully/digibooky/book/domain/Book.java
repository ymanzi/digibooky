package com.switchfully.digibooky.book.domain;

import java.util.Objects;

public class Book {
    private final String isbn;
    private String title;
    private Author author;
    private String summary;
    private int amountInStore;
    private Boolean isDeleted;

    public Book(String isbn, String title, Author author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
        amountInStore = 1;
        isDeleted = false;
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

    public int getAmountInStore() {
        return amountInStore;
    }

    public Boolean isDeleted() {
        return isDeleted;
    }
    public void decreaseAmountInStore(int amount){
        amountInStore-= amount;
    }
    public void  increaseAmountInStore(int amount){
        amountInStore += amount;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
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
