package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final MovieRepository movieRepository;

    public Query(LinkRepository linkRepository, MovieRepository movieRepository) {
        this.linkRepository = linkRepository;
        this.movieRepository = movieRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }



    public List<Movie> allMovies() {
        return movieRepository.getAllMovies();
    }

    public Movie getMovieById(String id) {
        return movieRepository.getMovieById(id);
    }

}
