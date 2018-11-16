package com.dkorniichuk.movieland.service.util.deserializer;

import com.dkorniichuk.movieland.entity.Movie;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class MovieDeserializer extends JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        final String nameNative = node.get("nameNative").asText();
        final String nameRussian = node.get("nameRussian").asText();
        final int yearOfRelease = node.get("yearOfRelease").asInt();
        final String description = node.get("description").asText();
        final double rating = node.get("rating").asDouble();
        final double price = node.get("price").asDouble();
        final String picturePath = node.get("picturePath").asText();


        return new Movie(nameNative + "/" + nameRussian, yearOfRelease, description, rating, price, picturePath);
    }
}
