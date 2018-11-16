package com.dkorniichuk.movieland.service.util.deserializer;

import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.entity.Review;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class ReviewDeserializer extends JsonDeserializer<Review> {
    @Override
    public Review deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        final int movieId = node.get("movieId").asInt();
        final String text = node.get("text").asText();
        Movie movie = new Movie();
        movie.setId(movieId);
        return new Review(text, movie);
    }
}

