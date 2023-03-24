package com.switchfully.digibooky.book.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class BookControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(BookControllerExceptionHandler.class);

    @ExceptionHandler(BookWithThisIsbnAlreadyExist.class)
    protected void bookWithThisIsbnAlreadyExist(BookWithThisIsbnAlreadyExist ex,
                                        HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoLastnameForAuthorException.class)
    protected void noLastnameForAuthorException(NoLastnameForAuthorException ex,
                                                HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoTitleForBookException.class)
    protected void noTitleForBookException(NoTitleForBookException ex,
                                                HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedEndPointException.class)
    protected void unauthorizedEndPointException(UnauthorizedEndPointException ex,
                                                HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
    }

    @ExceptionHandler(NoIsbnForBookException.class)
    protected void noIsbnForBookException(NoIsbnForBookException ex,
                                                HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(NoMemberWithThatIdException.class)
    protected void noMemberWithThatIdException(NoMemberWithThatIdException ex,
                                               HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(NullAuthorException.class)
    protected void nullAuthorException(NullAuthorException ex,
                                                HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }

    @ExceptionHandler(CanNotUpdateNonExistingBook.class)
    protected void canNotUpdateNonExistingBook(CanNotUpdateNonExistingBook ex,
                                               HttpServletResponse response) throws IOException {
        logger.error("Exception raised: ", ex);
        response.sendError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
    }
}
