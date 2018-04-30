package com.hotmail.pederwaern.movie_graphql.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSONFetcher {

    ObjectMapper objectMapper = new ObjectMapper();

    public JSONFetcher() {
    }

    public String fetchJsonByURL(String endpoint) throws IOException {
        HttpClient client = HttpClientBuilder.create().build();
        HttpGet request = new HttpGet(endpoint);
        StringBuilder result = new StringBuilder();
        HttpResponse response = null;
        response = client.execute(request);
        BufferedReader rd = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        return result.toString();

    }

    public String fetchJsonFromFile(File file) {
        try {
            return objectMapper.readTree(file).toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
