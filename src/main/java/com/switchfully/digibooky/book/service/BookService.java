package com.switchfully.digibooky.book.service;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.domain.Book;
import com.switchfully.digibooky.book.domain.BookRepository;
import com.switchfully.digibooky.book.exceptions.NoAuthorByIdException;
import com.switchfully.digibooky.book.exceptions.NoBookByAuthorException;
import com.switchfully.digibooky.book.exceptions.UnauthorizedEndPointException;
import com.switchfully.digibooky.book.service.dto.BookDto;
import com.switchfully.digibooky.book.service.mapper.AuthorMapper;
import com.switchfully.digibooky.book.service.mapper.BookMapper;
import com.switchfully.digibooky.member.domain.MemberRepository;
import com.switchfully.digibooky.member.domain.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final MemberRepository memberRepository;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, AuthorMapper authorMapper, MemberRepository memberRepository) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.authorMapper = authorMapper;
        this.memberRepository = memberRepository;
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
            throw new NullAuthorException();
        }

        Author authorRegex = getAuthorRegexFromAuthorDtoAsterisk(authorDto);
        List<Book> listOfBooks = bookRepository.getByAuthor(authorRegex);
        return bookMapper.toDto(listOfBooks);
    }

    public Author getAuthorRegexFromAuthorDtoAsterisk(AuthorDto authorDtoAsterisk){
        String regexFirstname = changeStringWithAsteriskToRegex(authorDtoAsterisk.getFirstname());
        String regexLastname = changeStringWithAsteriskToRegex(authorDtoAsterisk.getLastname());

        return new Author(regexFirstname, regexLastname);
    }

    public BookDto save(BookDto bookDto, String id){
        if (getRoleOfMemberById(id) == Role.MEMBER){
            throw new UnauthorizedEndPointException();
        }
        Book newBook = bookMapper.fromDto(bookDto);
        Book createdBook = bookRepository.save(newBook);
        return bookMapper.toDto(createdBook);
    }

    public BookDto update(BookDto bookToUpdateDto, String id){
        if (getRoleOfMemberById(id) == Role.MEMBER){
            throw new UnauthorizedEndPointException();
        }

        Book bookToUpdate = bookMapper.fromDto(bookToUpdateDto);
        Book bookUpdated = bookRepository.update(bookToUpdate);

        return bookMapper.toDto(bookUpdated);
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

    public Role getRoleOfMemberById(String id){
        if (memberRepository.getMemberById(UUID.fromString(id)) == null){
            throw new NoMemberWithThatIdException();
        }
        return memberRepository.getMemberById(UUID.fromString(id)).getRole();
    }

    public List<BookDto> delete(String input, String id, String typeOfDelete) {
        if (getRoleOfMemberById(id) == Role.MEMBER){
            throw new UnauthorizedEndPointException();
        }

        List<BookDto> listOfBookToDelete = switch(typeOfDelete){
            case "isbn" -> getByIsbn(input);
            default -> getByTitle(input); // case "title"
        };

        List<Book> listOfDeletedBooks = bookRepository.delete(bookMapper.fromDto(listOfBookToDelete));
        return bookMapper.toDto(listOfDeletedBooks);
    }

    private void checkAuthorizationForTheMember(String id) {
        if (getRoleOfMemberById(id) == Role.MEMBER){
            throw new UnauthorizedEndPointException();
        }
    }
}
