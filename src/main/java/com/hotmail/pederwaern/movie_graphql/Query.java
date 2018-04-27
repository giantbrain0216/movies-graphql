package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.Rating;
import com.hotmail.pederwaern.movie_graphql.models.User;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;

    public Query(MovieRepository movieRepository, UserRepository userRepository) {
        this.movieRepository = movieRepository;
        this.userRepository = userRepository;
    }

    public List<User> allUsers() {
        return this.userRepository.allUsers();
    }

    public List<Movie> allMovies() {
        return movieRepository.getAllMovies();
    }

    public Movie getMovieById(String id) {
        return movieRepository.getMovieById(id);
    }

    public List<Rating> allRatings() {return userRepository.allRatings();}

    public Rating ratingById(String id) {
        return userRepository.getRatingById(id);

    }
}
