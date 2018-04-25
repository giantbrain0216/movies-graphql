package com.hotmail.pederwaern.movie_graphql.api_fetcher;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hotmail.pederwaern.movie_graphql.Movie;
import com.hotmail.pederwaern.movie_graphql.MovieRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Fetcher {
    private final MovieRepository movieRepository;

    public Fetcher(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void fetchMovieData() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=3a79d2f7555b7893d9df6ee500d70c55&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        StringBuilder result = new StringBuilder();
        try {
            this.movieRepository.getAllMovies().addAll(getRemoteMovieData(client, request, result));
        } catch (IOException e) {
            this.movieRepository.getAllMovies().addAll(getSampleMovieDataFromJSON());
        }
    }

    private List<Movie> getSampleMovieDataFromJSON() {
        //Something went wrong, get sample data from json
        ObjectMapper objectMapper = new ObjectMapper();
        List<Movie> movies = new ArrayList<>();
        try {
            JsonNode node = objectMapper.readTree(new File("src/main/resources/sampledata.json"));
            ArrayNode arrayNode = (ArrayNode) node.get("results");
            movies = objectMapper.readValue(arrayNode.toString(), new TypeReference<List<Movie>>(){});
            System.out.println(movies);

        } catch (IOException e1) {
            throw new RuntimeException("Could not parse remote data");
        }
        return movies;
    }

    private List<Movie> getRemoteMovieData(HttpClient client, HttpGet request, StringBuilder result) throws IOException {
        HttpResponse response;
        response = client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        String json = result.toString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(json);
        ArrayNode arrayNode = (ArrayNode) node.get("results");
        return objectMapper.readValue(arrayNode.toString(), new TypeReference<List<Movie>>(){});
    }
}

