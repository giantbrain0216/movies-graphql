package com.hotmail.pederwaern.movie_graphql;

import java.util.ArrayList;
import java.util.List;

public class MovieRepository {
    private List<Movie> allMovies;

    public MovieRepository() {
        this.allMovies = new ArrayList<>();
    }

    public List<Movie> getAllMovies() {
        return this.allMovies;
    }

    public void addMovie(Movie movie) {
        this.allMovies.add(movie);
    }

}
