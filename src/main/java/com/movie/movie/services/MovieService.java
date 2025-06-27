package com.movie.movie.services;

import com.movie.movie.dto.MovieDto;
import com.movie.movie.repositories.MovieRepo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    MovieDto getMovie(Integer movieId);

    List<MovieDto> getAllMovies();




}
