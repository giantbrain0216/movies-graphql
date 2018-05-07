package com.hotmail.pederwaern.movie_graphql.repositories;

import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.Rating;
import com.hotmail.pederwaern.movie_graphql.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private Map<String, User> usersMap;
    private Map<String, Rating> ratingsMap;

    public UserRepository() {
        usersMap = new HashMap<>();
        ratingsMap = new HashMap<>();
        createSampleData();
    }

    private void createSampleData() {
        User user1 = new User("1", "Peder Waern");
        User user2 = new User("2", "John Doe");
        usersMap.put(user1.getId(), user1);
        usersMap.put(user2.getId(), user2);
    }

    public List<User> allUsers() {
        List<User> allUsers = new ArrayList<>();
        allUsers.addAll(this.usersMap.values());
        return allUsers;
    }

    public User getUserById(String id) {
        return this.usersMap.get(id);
    }

    public List<Rating> allRatings() {
        List<Rating> allRatings = new ArrayList<>();
        for (User user: this.usersMap.values()) {
            allRatings.addAll(user.getRatings());
        }
        return allRatings;
    }

    public User addUser(User user) {
        this.usersMap.put(user.getId(), user);
        return user;
    }

    public Rating addRating(Rating rating) {
        User user = this.usersMap.get(rating.getUser().getId());
        user.addRating(rating);
        ratingsMap.put(rating.getId(), rating);
        rating.getMovie().getRatings().add(rating);
        return rating;
    }

    public String deleteRating(Rating rating) {
        User user = this.usersMap.get(rating.getUser().getId());
        user.getRatings().remove(rating);
        this.ratingsMap.remove(rating.getId());
        Movie movie = rating.getMovie();
        movie.getRatings().remove(rating);
        return "Successfully deleted rating with id: " + rating.getId();
    }

    public Rating getRatingById(String id) {
        return this.ratingsMap.get(id);
    }

    public Rating getRatingByUserAndMovie(String userId, String movieId) {
        return ratingsMap.values().stream().filter(rating -> rating.getUser().getId().equals(userId))
                .filter(rating -> rating.getMovie().getId().equals(movieId))
                .findFirst()
                .get();
    }
}
