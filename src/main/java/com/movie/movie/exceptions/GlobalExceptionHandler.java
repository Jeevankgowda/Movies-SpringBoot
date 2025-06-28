package com.movie.movie.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.FileNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ProblemDetail HandlerMovieNotFound(MovieNotFoundException movieNotFoundException){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,movieNotFoundException.getMessage() );
    }

    @ExceptionHandler(FileExistsException.class)
    public ProblemDetail HandleFileExistsException(FileExistsException fileExistsException){

        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,fileExistsException.getMessage() );
    }

    @ExceptionHandler(FileEmptyException.class)
    public ProblemDetail HandleFileEmptyException(FileEmptyException fileEmptyException){
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,fileEmptyException.getMessage() );
    }
}
