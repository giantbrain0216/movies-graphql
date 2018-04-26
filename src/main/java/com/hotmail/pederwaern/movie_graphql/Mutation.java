package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.GraphQLRootResolver;
import com.hotmail.pederwaern.movie_graphql.models.Link;
import com.hotmail.pederwaern.movie_graphql.models.User;
import com.hotmail.pederwaern.movie_graphql.repositories.LinkRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;

import java.util.UUID;

public class Mutation implements GraphQLRootResolver {

    private final LinkRepository linkRepository;
    private final UserRepository userRepository;

    public Mutation(LinkRepository linkRepository, UserRepository userRepository) {
        this.linkRepository = linkRepository;
        this.userRepository = userRepository;
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
}
