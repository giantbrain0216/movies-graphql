package com.hotmail.pederwaern.movie_graphql.models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private final List<Rating> ratings;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
        this.ratings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void addRating(Rating rating) {
        this.ratings.add(rating);
    }
}
