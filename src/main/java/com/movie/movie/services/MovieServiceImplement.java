package com.movie.movie.services;

import com.movie.movie.dto.MovieDto;
import com.movie.movie.models.Movie;
import com.movie.movie.repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class MovieServiceImplement implements MovieService {

    private final MovieRepo repo;
    private final FileService fileService;

    public MovieServiceImplement(MovieRepo repo, FileService fileService) {
        this.repo = repo;
        this.fileService = fileService;
    }

    @Value("${project.posterPath}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    @Override
    public MovieDto addMovie(MovieDto movieDto, MultipartFile file) throws IOException {
        //1. upload the file
        String uploadedfileName=fileService.uploadFile(path, file);

        //2. set the poster of moviedto to filename
        movieDto.setPoster(uploadedfileName);
        //3. map the moviedto to model.movie class
        Movie movie=new Movie(
                movieDto.getMovieId(),
                movieDto.getMovieName(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());

        // 4.save the movie object

        Movie savedMovie=repo.save(movie);
        //5. generate the posterurl

        String url=baseUrl+"/file/"+uploadedfileName;

        //6. Map the movie object to movie dto

        MovieDto response=new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getMovieName(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                url
        );
        return response;
    }

    @Override
    public MovieDto getMovie(Integer movieId) {

        // 1.retrieve the movie object from db
        String posterUrl = "";
        Movie savedMovie=repo.findById(movieId).orElseThrow(()-> new RuntimeException("No movie found"));

        //2.create posterurl

            posterUrl=baseUrl+"/file/"+savedMovie.getPoster();

        //3. map to moviedto

            return new MovieDto(
                    savedMovie.getMovieId(),
                    savedMovie.getMovieName(),
                    savedMovie.getTitle(),
                    savedMovie.getDirector(),
                    savedMovie.getStudio(),
                    savedMovie.getMovieCast(),
                    savedMovie.getReleaseYear(),
                    savedMovie.getPoster(),
                    posterUrl
            );

    }

    @Override
    public List<MovieDto> getAllMovies() {

        //1. fetch the list of movies
        List<Movie> movie= new ArrayList<>();

        //2. iterate the list , create the posterurl and map it to the MovieDto
        movie    =repo.findAll();
        List<MovieDto> response=new ArrayList<>();
        for(Movie m:movie){
            String poster=baseUrl+"/file/"+m.getPoster();
            response.add(new MovieDto(
                    m.getMovieId(),
                    m.getMovieName(),
                    m.getTitle(),
                    m.getDirector(),
                    m.getStudio(),
                    m.getMovieCast(),
                    m.getReleaseYear(),
                    m.getPoster(),
                    poster
            ));
        }

        return response;
    }
}
