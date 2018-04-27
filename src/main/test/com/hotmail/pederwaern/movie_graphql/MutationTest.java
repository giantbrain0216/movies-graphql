package com.hotmail.pederwaern.movie_graphql;

import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.User;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MutationTest {

    private Mutation mutation;
    @org.junit.Before
    public void setUp() throws Exception {

    }

    @Test
    public void CreateRatingPersistsOneRating() {
        User dummyUser = new User("1", "User Name");
        UserRepository userRepository = new UserRepository();
        MovieRepository movieRepository = new MovieRepository();
        userRepository.addUser(dummyUser);
        Movie movie = new Movie();
        movie.setId("2");
        movieRepository.addMovie(movie);
        mutation = new Mutation( userRepository, movieRepository);
        mutation.createRating(2, "bla", "1", "2");
        assertThat(userRepository.allRatings().size(), is(1));
    }


}