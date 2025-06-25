package com.movie.movie.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDto {


    private Integer movieId;

    // Column anotation is used to specify the rules at column level

    //NotBlank is used to display mesasage
    @NotBlank(message = "Please enter the movie name")
    private String movieName;


    @NotBlank(message = "Please enter the movie title")
    private String title;


    @NotBlank(message = "Please enter the Director Name")
    private String director;


    @NotBlank(message = "Please enter the movie Studio Name")
    private String studio;

    // @ElementCollection with @CollectionTable(name = "movie_cast") maps a collection of basic types (like Set<String>) to a separate table (movie_cast) and creates a foreign key linking each entry to the parent entity (Movie).
    private Set<String> movieCast;


    private Integer releaseYear;

    private String poster;

    private String PosterUrl;

}
