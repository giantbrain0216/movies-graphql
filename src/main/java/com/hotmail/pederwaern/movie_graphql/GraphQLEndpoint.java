package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hotmail.pederwaern.movie_graphql.api_fetcher.Fetcher;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static MovieRepository movieRepository = new MovieRepository();

    public GraphQLEndpoint() {
        super(buildSchema());

    }

    private static void loadDataFromApi() {
        Fetcher fetcher = new Fetcher(movieRepository);
        fetcher.fetchMovieData();
    }

    private static GraphQLSchema buildSchema() {
        loadDataFromApi();
        LinkRepository linkRepository = new LinkRepository();
        System.out.println("Running buildSchema");
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }
}
