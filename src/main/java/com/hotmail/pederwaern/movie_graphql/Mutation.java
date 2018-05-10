package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.Rating;
import com.hotmail.pederwaern.movie_graphql.models.User;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Mutation implements GraphQLRootResolver {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final Logger logger = LoggerFactory.getLogger(Mutation.class);

    public Mutation(UserRepository userRepository, MovieRepository movieRepository) { ;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;

    }

    public User createUser(String name) {
        String randomID = UUID.randomUUID().toString();
        User newUser = new User(randomID, name);
        return userRepository.addUser(newUser);
    }


    public Rating createRating(int movieRating, String comment, String userId, String movieId) {
        String randomID = UUID.randomUUID().toString();
        User user = userRepository.getUserById(userId);
        Movie movie = movieRepository.getMovieById(movieId);
        Rating ratingToAdd = new Rating(randomID, movie, user, movieRating, comment);
        userRepository.addRating(ratingToAdd);
        logger.info("Created rating with id : " + ratingToAdd.getId());
        return ratingToAdd;
    }

    public Rating deleteRating(String id) {
        Rating ratingToDelete = this.userRepository.getRatingById(id);
        logger.info("Deleted rating with id: " + ratingToDelete.getId());
        return this.userRepository.deleteRating(ratingToDelete);
    }
}
