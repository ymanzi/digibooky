package com.switchfully.digibooky.book.service.dto;
import com.switchfully.digibooky.book.domain.Author;

import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return isbn.equals(bookDto.isbn) && title.equals(bookDto.title) && author.equals(bookDto.author) && summary.equals(bookDto.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn, title, author, summary);
    }
}
