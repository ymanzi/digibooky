package com.switchfully.digibooky.book.service.dto;

import com.switchfully.digibooky.book.domain.Author;

import java.util.Objects;

public class UpdateBookDto {
    private String title;
    private Author author;
    private String summary;

    public UpdateBookDto(String title, Author author, String summary) {
        this.title = title;
        this.author = author;
        this.summary = summary;
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

    public UpdateBookDto setTitle(String title){
        this.title = title;
        return this;
    }

    public UpdateBookDto setAuthor(Author author){
        this.author = author;
        return this;
    }

    public UpdateBookDto setSummary(String summary){
        this.summary = summary;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateBookDto that = (UpdateBookDto) o;
        return Objects.equals(title, that.title) && Objects.equals(author, that.author) && Objects.equals(summary, that.summary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, summary);
    }
}
