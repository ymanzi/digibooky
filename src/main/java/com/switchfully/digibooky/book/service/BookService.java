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

    public List<BookDto> getByIsbn(String isbn){
        String isbnRegex = changeStringWithAsteriskToRegex(isbn);

        List<Book> listOfBooks = bookRepository.getByIsbn(isbnRegex);
        return bookMapper.toDto(listOfBooks);
    }

    public List<BookDto> getByTitle(String title){
        String titleRegex = changeStringWithAsteriskToRegex(title);

        List<Book> listOfBooks = bookRepository.getByTitle(titleRegex);
        return bookMapper.toDto(listOfBooks);
    }

    public List<BookDto> getByAuthor(AuthorDto authorDto){
        if (authorDto == null){
            throw new NoBookByAuthorException();
        }
        AuthorDto newAuthorDto = getAuthorDtoRegex(authorDto);
        Author author =  authorMapper.fromDto(newAuthorDto);
        List<Book> listOfBooks = bookRepository.getByAuthor(author);
        return bookMapper.toDto(listOfBooks);
    }

    private AuthorDto getAuthorDtoRegex(AuthorDto authorDto) {
        String authorFirstname = authorDto.getFirstname();
        String authorLastname = authorDto.getLastname();

        String authorFirstnameRegex = changeStringWithAsteriskToRegex(authorFirstname);
        String authorLastnameRegex = changeStringWithAsteriskToRegex(authorLastname);

        return new AuthorDto(authorFirstnameRegex, authorLastnameRegex);
    }

    public BookDto save(BookDto bookDto){
        Book newBook = bookMapper.fromDto(bookDto);
        Book createdBook = bookRepository.save(newBook);
        return bookMapper.toDto(createdBook);
    }

    public String changeStringWithAsteriskToRegex(String input){
        int indexOfAsterisk = input.indexOf("*");

        if (indexOfAsterisk == -1) {
            return input;
        }

        if (indexOfAsterisk == 0){
            input = input.replaceFirst("[*]", "(^|.+)");
        }


        return input.replaceAll("[*]", ".+");
    }
}
