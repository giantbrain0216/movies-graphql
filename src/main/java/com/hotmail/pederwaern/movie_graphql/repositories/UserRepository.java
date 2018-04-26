package com.hotmail.pederwaern.movie_graphql.repositories;

import com.hotmail.pederwaern.movie_graphql.models.Rating;
import com.hotmail.pederwaern.movie_graphql.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private List<User> allUsers;

    public UserRepository() {
        allUsers = new ArrayList<>();
        allUsers.add(new User("1", "Peder Waern"));
        allUsers.add(new User("2", "John Doe"));
        allUsers.get(1).addRating(new Rating(1, "Lousy movie"));
    }

    public List<User> allUsers() {
        return this.allUsers;
    }

    public User getUserById(String id) {
        return allUsers.stream().filter(user -> user.getId()
                .equals(id))
                .findFirst()
                .get();
    }

    public List<Rating> allRatings() {
        List<Rating> allRatings = new ArrayList<>();
        for (User user: allUsers) {
            allRatings.addAll(user.getRatings());
        }
        return allRatings;
    }

    public User addUser(User user) {
        this.allUsers.add(user);
        return user;
    }

}
