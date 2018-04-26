package com.hotmail.pederwaern.movie_graphql.models;

public class Rating {

    private String id;
    private Integer rating;
    private String comment;
    private User user;

    public Rating(Integer rating) {
        this.id = id;
        this.rating = rating;
    }

    public Rating(Integer rating, String comment) {
        this(rating);
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
}
