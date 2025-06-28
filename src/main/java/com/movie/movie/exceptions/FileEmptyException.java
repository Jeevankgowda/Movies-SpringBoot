package com.movie.movie.exceptions;

public class FileEmptyException extends RuntimeException{

    public FileEmptyException(String message){
        super(message);
    }

}
