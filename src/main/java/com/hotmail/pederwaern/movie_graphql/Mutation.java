package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;

import java.util.UUID;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;

    public Mutation(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public Link createLink(String url, String description) {
        String randomID = UUID.randomUUID().toString();
        Link newLink = new Link(randomID, url, description);
        linkRepository.saveLink(newLink);
        return newLink;
    }
}
