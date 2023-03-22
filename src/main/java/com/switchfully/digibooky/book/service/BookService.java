package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.exceptions.NoBookByAuthorException;
import com.switchfully.digibooky.book.service.dto.AuthorDto;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.mapper.AuthorMapper;
import com.switchfully.digibooky.book.service.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, AuthorMapper authorMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
    }

    public List<BookDto> getAll(){
        List<Book> listOfBooks = bookRepository
                                    .getAll()
                                    .stream()
                                    .toList();
        return bookMapper.toDto(listOfBooks);
    }

    public BookDto getByIsbn(String isbn){
        Book book = bookRepository.getByIsbn(isbn);
        return bookMapper.toDto(book);
    }

    public BookDto getByTitle(String title){
        Book book = bookRepository.getByTitle(title);
        return bookMapper.toDto(book);
    }

    public BookDto getByAuthor(AuthorDto authorDto){
        if (authorDto == null){
            throw new NoBookByAuthorException();
        }

        Author author =  authorMapper.fromDto(authorDto);
        Book book = bookRepository.getByAuthor(author);
        return bookMapper.toDto(book);
    }

    public BookDto save(BookDto bookDto){
        Book newBook = bookMapper.fromDto(bookDto);
        Book createdBook = bookRepository.save(newBook);
        return bookMapper.toDto(createdBook);
    }
}
