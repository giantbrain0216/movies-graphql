package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hotmail.pederwaern.movie_graphql.models.Link;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.Rating;
import com.hotmail.pederwaern.movie_graphql.models.User;
import com.hotmail.pederwaern.movie_graphql.repositories.LinkRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;

import java.util.UUID;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;

    }

    public Link createLink(String url, String description) {
        String randomID = UUID.randomUUID().toString();
        Link newLink = new Link(randomID, url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }

    public User createUser(String name) {
        String randomID = UUID.randomUUID().toString();
        User newUser = new User(randomID, name);
        return userRepository.addUser(newUser);
    }

    // createRating(movieRating: Int!, comment: String!, userId: String!, movieId: String!): Rating
    public Rating createRating(int movieRating, String comment, String userId, String movieId) {
        String randomID = UUID.randomUUID().toString();
        User user = userRepository.getUserById(userId);
        Movie movie = movieRepository.getMovieById(movieId);
        Rating ratingToAdd = new Rating(randomID, movie, user, movieRating, comment);
        userRepository.addRating(ratingToAdd);
        return ratingToAdd;
    }
}
