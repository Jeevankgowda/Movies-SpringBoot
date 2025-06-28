package com.movie.movie.services;

import com.movie.movie.dto.MovieDto;
import com.movie.movie.exceptions.FileExistsException;
import com.movie.movie.exceptions.MovieNotFoundException;
import com.movie.movie.models.Movie;
import com.movie.movie.repositories.MovieRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

        if(Files.exists(Paths.get(path+ File.separator+file.getOriginalFilename())))
        {
             throw new FileExistsException("FileFound: Please change the file name and upload");
        }
        String uploadedfileName=fileService.uploadFile(path, file);

        //2. set the poster of moviedto to filename
        movieDto.setPoster(uploadedfileName);
        //3. map the moviedto to model.movie class
        Movie movie=new Movie(
               null,
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
        Movie savedMovie=repo.findById(movieId).orElseThrow(()-> new MovieNotFoundException("No movie found"));

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

    @Override
    public MovieDto updateMovie(Integer movieId, MovieDto movieDto, MultipartFile file) throws IOException

    {
        //1. check if the movie exists eith movieid
        Movie mv=repo.findById(movieId).orElseThrow(()-> new RuntimeException("No movie found"));

        //2. check if the file is null, if null nothing to do
        // if not null, then delete the previous poster file associated with the movieId and upload the new one
        String filename=mv.getPoster();
        if(file !=null)
        {
            Files.deleteIfExists(Paths.get(path+File.separator+mv.getPoster()));
             filename=fileService.uploadFile(path, file);
        }

        //3.Add the poster file name to movieDto
        movieDto.setPoster(filename);

        //4. map the movie dto to movie
        Movie movie=new Movie(
                mv.getMovieId(),
                movieDto.getMovieName(),
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster());

        // 5. save it to the db
        Movie savedMovie=repo.save(movie);

        //. Create posterUrl
        String PosterUrl=baseUrl+"/file/"+savedMovie.getPoster();

        MovieDto response=new MovieDto(
                savedMovie.getMovieId(),
                savedMovie.getMovieName(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                PosterUrl
        );

        return response;
    }

    @Override
    public String deleteMovie(Integer movieId) throws IOException {
        //1. check if the movieId present

        Movie mv=repo.findById(movieId).orElseThrow(()-> new MovieNotFoundException("No movie found"));
        //2. Delete the file if exists
        Files.deleteIfExists(Paths.get(path+File.separator+mv.getPoster()));
        //3. delete in DB
        repo.delete(mv);
        return "Movie deleted successfully";
    }

}
