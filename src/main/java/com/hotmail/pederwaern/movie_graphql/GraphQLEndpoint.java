package com.hotmail.pederwaern.movie_graphql;

import com.coxautodev.graphql.tools.SchemaParser;
import com.hotmail.pederwaern.movie_graphql.api.JSONFetcher;
import com.hotmail.pederwaern.movie_graphql.api.JSONParser;
import com.hotmail.pederwaern.movie_graphql.models.ImageConfig;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.RemoteMovieEntity;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import com.hotmail.pederwaern.movie_graphql.repositories.UserRepository;
import graphql.schema.GraphQLSchema;
import graphql.servlet.SimpleGraphQLServlet;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static MovieRepository movieRepository = new MovieRepository();
    private static final String MOVIE_ENDPOINT_URL = "https://api.themoviedb.org/3/discover/movie?api_key=3a79d2f7555b7893d9df6ee500d70c55&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
    private static final String CONFIGURATION_ENDPOINT_URL = "https://api.themoviedb.org/3/configuration?api_key=3a79d2f7555b7893d9df6ee500d70c55";
    private static final String SAMPLE_DATA = "src/main/resources/sampledata.json";
    private static ImageConfig IMAGE_CONFIG;

    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        loadDataFromApi();
        UserRepository userRepository = new UserRepository();
        System.out.println("Running buildSchema");
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(movieRepository, userRepository, IMAGE_CONFIG), new Mutation(userRepository, movieRepository))
                .build()
                .makeExecutableSchema();
    }

    private static void loadDataFromApi() {
        JSONFetcher fetcher = new JSONFetcher();
        addMovieDataToRepository(retrieveMovieData(fetcher));
        retrieveImageConfiguration();
    }

    private static String retrieveMovieData(JSONFetcher fetcher) {
        String moviesJson;
        try {
            moviesJson = new JSONFetcher().fetchJsonByURL(MOVIE_ENDPOINT_URL);
        } catch(IOException e) {
            moviesJson = fetcher.fetchJsonFromFile(new File(SAMPLE_DATA));
        }
        return moviesJson;
    }

    private static void addMovieDataToRepository(String moviesJson) {
        JSONParser parsedMovies = new JSONParser(moviesJson, new Movie(), "results", true);
        List<RemoteMovieEntity> movies = parsedMovies.getList();
        for (RemoteMovieEntity movie: movies) {
            movieRepository.addMovie((Movie) movie);
        }
    }

    private static void retrieveImageConfiguration() {
        String configJson;
        try {
            configJson = new JSONFetcher().fetchJsonByURL(CONFIGURATION_ENDPOINT_URL);
            JSONParser parsedConfig = new JSONParser(configJson, new ImageConfig(), "images", false);
            IMAGE_CONFIG = (ImageConfig) parsedConfig.one();
        } catch (IOException e) {
            IMAGE_CONFIG = ImageConfig.DEFAULT_CONFIG();
        }
    }
}
