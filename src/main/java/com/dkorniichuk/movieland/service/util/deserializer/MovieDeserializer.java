package com.dkorniichuk.movieland.service.util.deserializer;

import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.entity.Movie;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;

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

        JsonNode nodeGenres = node.get("genres");
        JsonNode nodeCountries = node.get("countries");

        Set<Genre> genres = new ObjectMapper()
                .readerFor(new TypeReference<Set<Genre>>() {
                }).readValue(nodeGenres);
        Set<Country> countries = new ObjectMapper()
                .readerFor(new TypeReference<Set<Country>>() {
                }).readValue(nodeCountries);


        return new Movie(nameNative + "/" + nameRussian, yearOfRelease, description, rating, price, picturePath, genres, countries);
    }
}
