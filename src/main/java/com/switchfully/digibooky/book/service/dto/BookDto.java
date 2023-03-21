package com.switchfully.digibooky.book.service.dto;
import com.switchfully.digibooky.book.domain.Author;

public class BookDto {
    private final String isbn;
    private final String title;
    private final Author author;
    private final String summary;

    public BookDto(String isbn, String title, Author author, String summary) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.summary = summary;
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

}
