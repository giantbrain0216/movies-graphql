package com.hotmail.pederwaern.movie_graphql.api_fetcher;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.repositories.MovieRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


//TODO Refactor to fetch dynamic URLs
public class Fetcher {
    private final MovieRepository movieRepository;
    private final String endpointURL;
    private ObjectMapper objectMapper;

    public Fetcher(MovieRepository movieRepository, String endpointURL) {
        this.movieRepository = movieRepository;
        this.objectMapper = new ObjectMapper();
        this.endpointURL = endpointURL;

    }

    public void fetchMovieData() {
        try {
            this.movieRepository.addCollection(parseResultNodeToListOfMovies(getRemoteMovieDataAsJSON(this.endpointURL)));
        } catch (IOException e) {
            System.out.println("Could not fetch remote data, proceeding to get sample data");
            try {
                this.movieRepository.addCollection(getJSONDataFromLocalFile(new File("src/main/resources/sampledata.json")));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private String getRemoteMovieDataAsJSON(String endpoint) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(endpoint);
        StringBuilder result = new StringBuilder();
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }

        return result.toString();
    }

    private List<Movie> getJSONDataFromLocalFile(File file) throws IOException {
        return parseResultNodeToListOfMovies(objectMapper.readTree(file).toString());
    }

    private List<Movie> parseResultNodeToListOfMovies(String json) {
        try {
            JsonNode node = objectMapper.readTree(json);
            ArrayNode resultNode = (ArrayNode) node.get("results");
            return objectMapper.readValue(resultNode.toString(), new TypeReference<List<Movie>>(){});

        } catch (IOException e) {
            throw new RuntimeException("Could not parse data");
        }

    }
}

