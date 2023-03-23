package com.switchfully.digibooky.book.api;

import com.switchfully.digibooky.book.service.BookService;
import com.switchfully.digibooky.book.service.dto.BookDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    //============= GET =================================================================

    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll(){
        return bookService.getAll();
    }


    @GetMapping(path = "/isbn/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getByIsbn(@PathVariable String isbn){
        return bookService.getByIsbn(isbn);
    }


    @GetMapping(path = "/author/{authorId}", consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getByAuthor(@PathVariable String authorId){
        return bookService.getByAuthor(authorId);
    }


    @GetMapping(path = "/title/{title}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getByTitle(@PathVariable String title){
        return bookService.getByTitle(title);
    }


    //============= POST =================================================================================

    @PostMapping(path = "", consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto bookDto, @RequestHeader String id){
        return bookService.save(bookDto, id);
    }

    //============= PUT ==================================================================================

    @PutMapping(consumes="application/json", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public BookDto updateOneBook(@RequestBody BookDto updateBookDto, @RequestHeader String id){
        return bookService.update(updateBookDto, id);
    }

    //============= DELETE ==============================================================================

    @DeleteMapping(path = "/isbn/{isbn}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> deleteByIsbn(@PathVariable String isbn, @RequestHeader String id){
        return bookService.delete(isbn, id, "isbn");
    }

    @DeleteMapping(path = "/title/{title}", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> deleteByTitle(@PathVariable String title, @RequestHeader String id){
        return bookService.delete(title, id, "title");
    }

    @PostMapping(path = "", consumes = "application/json",produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto create(@RequestBody BookDto bookDto){
        return bookService.save(bookDto);
    }
}
