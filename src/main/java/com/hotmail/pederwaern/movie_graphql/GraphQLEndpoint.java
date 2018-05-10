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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

@WebServlet(urlPatterns = "/graphql")
public class GraphQLEndpoint extends SimpleGraphQLServlet {

    private static MovieRepository movieRepository = new MovieRepository();
    private static String MOVIE_ENDPOINT_URL;
    private static String CONFIGURATION_ENDPOINT_URL;
    private static String SAMPLE_DATA;
    private static ImageConfig IMAGE_CONFIG;
    private static Logger logger = LoggerFactory.getLogger(GraphQLEndpoint.class);


    public GraphQLEndpoint() {
        super(buildSchema());
    }

    private static GraphQLSchema buildSchema() {
        loadFromProperties();
        initData();
        UserRepository userRepository = new UserRepository();
        logger.info("Building schema...");
        return SchemaParser.newParser()
                .file("schema.graphqls")
                .resolvers(new Query(movieRepository, userRepository, IMAGE_CONFIG), new Mutation(userRepository, movieRepository))
                .build()
                .makeExecutableSchema();
    }

    private static void loadFromProperties() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("src/main/resources/config.properties");
            prop.load(input);
            MOVIE_ENDPOINT_URL = prop.getProperty("movieEndpointUrl");
            CONFIGURATION_ENDPOINT_URL = prop.getProperty("configurationEndpointUrl");
            SAMPLE_DATA = prop.getProperty("sampleDataPath");

        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Could not load properties from config.properties");
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static void initData() {
        JSONFetcher fetcher = new JSONFetcher();
        addMovieDataToRepository(retrieveMovieData(fetcher));
        retrieveImageConfiguration();
    }

    private static void addMovieDataToRepository(String moviesJson) {
        JSONParser parsedMovies = new JSONParser(moviesJson, new Movie(), "results", true);
        List<RemoteMovieEntity> movies = parsedMovies.getList();
        for (RemoteMovieEntity movie: movies) {
            movieRepository.addMovie((Movie) movie);
        }
    }

    private static String retrieveMovieData(JSONFetcher fetcher) {
        String moviesJson;
        try {
            moviesJson = new JSONFetcher().fetchJsonByURL(MOVIE_ENDPOINT_URL);
        } catch(Exception e) {
            moviesJson = fetcher.fetchJsonFromFile(new File(SAMPLE_DATA));
        }
        return moviesJson;
    }

    private static void retrieveImageConfiguration() {
        String configJson;
        try {
            configJson = new JSONFetcher().fetchJsonByURL(CONFIGURATION_ENDPOINT_URL);
            JSONParser parsedConfig = new JSONParser(configJson, new ImageConfig(), "images", false);
            IMAGE_CONFIG = (ImageConfig) parsedConfig.one();
        } catch (Exception e) {
            IMAGE_CONFIG = ImageConfig.DEFAULT_CONFIG();
        }
    }
}
