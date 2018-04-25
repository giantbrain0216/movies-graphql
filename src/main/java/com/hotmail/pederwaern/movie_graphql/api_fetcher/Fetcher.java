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
import java.util.List;

public class Fetcher {
    public Fetcher(MovieRepository movieRepository) {

    }

    public void getMovieData() {
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=3a79d2f7555b7893d9df6ee500d70c55&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1";
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(url);
        HttpResponse response = null;
        StringBuilder result = new StringBuilder();
        try {
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
            List<Movie> movies = objectMapper.readValue(arrayNode.toString(), new TypeReference<List<Movie>>(){});
            System.out.println(movies);
        } catch (IOException e) {
            //Something went wrong, get sample data from json
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode node = objectMapper.readTree(new File("/Users/pederwaern/GraphQL/graphql-java/src/main/resources/sampledata.json"));
                ArrayNode arrayNode = (ArrayNode) node.get("results");
                List<Movie> movies = objectMapper.readValue(arrayNode.toString(), new TypeReference<List<Movie>>(){});
                System.out.println(movies);

            } catch (IOException e1) {
                e1.printStackTrace();
            }

            e.printStackTrace();
        }
    }
}
