package com.hotmail.pederwaern.movie_graphql.models;

public class Rating {

    private final String id;
    private int rating;
    private String comment;
    private final User user;
    private final Movie movie;

    public Rating(String id, Movie movie, User user, int rating, String comment) {
        this.id = id;
        this.movie = movie;
        this.user = user;
        this.rating = rating;
        this.comment = comment;
    }


    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }

    public String getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Movie getMovie() {
        return movie;
    }
}
