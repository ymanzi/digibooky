package com.switchfully.digibooky.book.service.mapper;

import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.service.dto.BookDto;
import org.springframework.stereotype.Component;

import java.util.List;

public class BookMapper {

    public BookDto toDto(Book book){
        return new BookDto(book.getIsbn(), book.getTitle(), book.getAuthor(), book.getSummary());
    }

    public List<BookDto> toDto(List<Book> listOfBooks){
        return listOfBooks
                .stream()
                .map(this::toDto)
                .toList();
    }

    public Book fromDto(BookDto bookDto){
        return new Book(bookDto.getIsbn(), bookDto.getTitle(), bookDto.getAuthor(), bookDto.getSummary());
    }

    public List<Book> fromDto(List<BookDto> listOfBooksDto){
        return listOfBooksDto
                .stream()
                .map(this::fromDto)
                .toList();
    }
}
