package com.movie.movie.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movie.movie.dto.MovieDto;
import com.movie.movie.exceptions.FileEmptyException;
import com.movie.movie.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/movie/")
public class MovieController {
    private final MovieService movieService;


    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }

    @PostMapping("/AddMovie")
    public ResponseEntity<MovieDto> AddMovieHandler(@RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {

        if(file.isEmpty()){
            throw new FileEmptyException("File is empty Enter the new file");
        }
       MovieDto movieDto1=ConvetMovieDto(movieDto);

       return new ResponseEntity<>(movieService.addMovie(movieDto1,file), HttpStatus.CREATED);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDto> getMovieHeandler(@PathVariable Integer movieId){

        return new ResponseEntity<>(movieService.getMovie(movieId), HttpStatus.OK);
    }

    @GetMapping("/getAllMovie")
    public ResponseEntity<List<MovieDto>> getAllMoviesHandler(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @PutMapping("/updateMovie/{movieId}")
    private ResponseEntity<MovieDto> updateMovieHandler(@PathVariable Integer movieId, @RequestPart String movieDto, @RequestPart MultipartFile file) throws IOException
    {

        if(file.isEmpty()) file=null;
        MovieDto movieDto1=ConvetMovieDto(movieDto);
        return ResponseEntity.ok(movieService.updateMovie(movieId,movieDto1,file));
    }

    @DeleteMapping("/delete/ {movieId}")
    public ResponseEntity<String> deleteMovieHandler(@PathVariable Integer movieId) throws IOException {
        return ResponseEntity.ok(movieService.deleteMovie(movieId));
    }

    private MovieDto ConvetMovieDto(String movieDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.readValue(movieDto, MovieDto.class);
    }





}
