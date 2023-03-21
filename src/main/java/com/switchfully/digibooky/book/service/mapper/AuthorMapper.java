package com.switchfully.digibooky.book.service.mapper;

import com.switchfully.digibooky.book.domain.Author;
import com.switchfully.digibooky.book.service.dto.AuthorDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthorMapper {

    public AuthorDto toDto(Author author){
        return new AuthorDto(author.getFirstname(), author.getLastname());
    }

    public List<AuthorDto> toDto(List<Author> listOfAuthors){
        return listOfAuthors
                .stream()
                .map(author -> toDto(author))
                .toList();
    }

    public Author fromDto(AuthorDto authorDto){
        return new Author(authorDto.getFirstname(), authorDto.getLastname());
    }
}
