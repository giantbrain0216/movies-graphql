

package com.hotmail.pederwaern.movie_graphql.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.hotmail.pederwaern.movie_graphql.models.ImageConfig;
import com.hotmail.pederwaern.movie_graphql.models.Movie;
import com.hotmail.pederwaern.movie_graphql.models.RemoteMovieEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses movie JSON data to Java POJO
 */
public class JSONParser {
    // TODO Generalize this class

    private final String json;
    private final RemoteMovieEntity entity;
    private final boolean asList;
    private String fromNode;

    private ObjectMapper objectMapper;

    public JSONParser(String json, RemoteMovieEntity entity, boolean asList) {
        this.objectMapper = new ObjectMapper();
        this.json = json;
        this.entity = entity;
        this.asList = asList;
    }

    public JSONParser(String json, RemoteMovieEntity entity, String fromNode, boolean asList) {
        this(json, entity, asList);
        this.fromNode = fromNode;
    }

    public RemoteMovieEntity one() {
        JsonNode node = null;
        try {
            node = objectMapper.readTree(json).get(fromNode);
            if (entity instanceof ImageConfig) {
                return objectMapper.readValue(node.toString(), ImageConfig.class);
            }

        } catch (IOException e) {
            System.out.println("ERROR in one");
        }
        return entity;
    }

    public List<RemoteMovieEntity> getList() {

        JsonNode node = null;
        try {
            node = objectMapper.readTree(json);
            ArrayNode arrayNode = null;

            if (fromNode != null) {
                arrayNode = (ArrayNode) node.get(fromNode);
                if (entity instanceof Movie) {
                    return objectMapper.readValue(arrayNode.toString(), new TypeReference<List<Movie>>() {
                    });
                }
            } else {
                //
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
