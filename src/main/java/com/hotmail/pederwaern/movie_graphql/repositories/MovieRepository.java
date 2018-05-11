package com.hotmail.pederwaern.movie_graphql.repositories;

import com.hotmail.pederwaern.movie_graphql.models.Movie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * In-memory representation collection of Movies and operations
 */
public class MovieRepository {
    private List<Movie> allMovies;

    public MovieRepository() {
        this.allMovies = new ArrayList<>();
    }

    public Movie getMovieById(String id) {
        return this.allMovies
                .stream()
                .filter(movie -> id.equals(movie.getId()))
                .findFirst()
                .get();
    }

    public List<Movie> getAllMovies() {
        return this.allMovies;
    }

    public void addMovie(Movie movie) {
        this.allMovies.add(movie);
    }

    public void addCollection(Collection<Movie> movies) {
        this.allMovies.addAll(movies);
    }

}
