package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.List;

public class Query implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Query(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public List<Link> allLinks() {
        return linkRepository.getAllLinks();
    }

}
