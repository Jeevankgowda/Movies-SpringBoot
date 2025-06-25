package com.movie.movie.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Movie {

    @Id
    //GeneratedValue is to set the columnas identity column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer movieId;

    // Column anotation is used to specify the rules at column level
    @Column(nullable = false, length = 100)
    //NotBlank is used to display mesasage
    @NotBlank(message = "Please enter the movie name")
    private String movieName;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Please enter the movie title")
    private String title;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Please enter the Director Name")
    private String director;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Please enter the movie Studio Name")
    private String studio;

    // @ElementCollection with @CollectionTable(name = "movie_cast") maps a collection of basic types (like Set<String>) to a separate table (movie_cast) and creates a foreign key linking each entry to the parent entity (Movie).
    @ElementCollection
    @CollectionTable(name= "movie_cast")
    private Set<String> movieCast;


    private Integer releaseYear;

    private String poster;

}
