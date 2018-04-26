package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hotmail.pederwaern.movie_graphql.api_fetcher.Fetcher;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static MovieRepository movieRepository = new MovieRepository();
    private static final String MOVIE_ENDPOINT_URL = "https://api.themoviedb.org/3/discover/movie?api_key=3a79d2f7555b7893d9df6ee500d70c55&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";

    public GraphQLEndpoint() {
        super(buildSchema());

    }

    private static void loadDataFromApi() {
        Fetcher fetcher = new Fetcher(movieRepository, MOVIE_ENDPOINT_URL);
        fetcher.fetchMovieData();
    }

    private static GraphQLSchema buildSchema() {
        loadDataFromApi();
        LinkRepository linkRepository = new LinkRepository();
        System.out.println("Running buildSchema");
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(linkRepository, movieRepository), new Mutation(linkRepository))
                .build()
                .makeExecutableSchema();
    }
}
