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
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MovieDeserializer extends JsonDeserializer<Movie> {
    @Override
    public Movie deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        String nameNative = Optional.ofNullable(node.get("nameNative")).map(n -> n.asText()).orElse(null);
        String nameRussian = Optional.ofNullable(node.get("nameRussian")).map(n -> n.asText()).orElse(null);
        Integer yearOfRelease = Optional.ofNullable(node.get("yearOfRelease")).map(n -> n.asInt()).orElse(null);
        String description = Optional.ofNullable(node.get("description"))
                .map(n -> n.asText()).orElse(null);
        Double rating = Optional.ofNullable(node.get("rating"))
                .map(n -> n.asDouble()).orElse(null);
        Double price = Optional.ofNullable(node.get(null))
                .map(n -> n.asDouble()).orElse(0.0);
        String picturePath = Optional.ofNullable(node.get("picturePath")).map(n -> n.asText()).orElse(null);

        JsonNode nodeGenres = node.get("genres");
        JsonNode nodeCountries = node.get("countries");

        Set<Genre> genres = new HashSet<>();
        Set<Country> countries = new HashSet<>();

        if (nodeGenres != null) {
            genres = new ObjectMapper()
                    .readerFor(new TypeReference<Set<Genre>>() {
                    }).readValue(nodeGenres);
        }

        if (nodeCountries != null) {
            countries = new ObjectMapper()
                    .readerFor(new TypeReference<Set<Country>>() {
                    }).readValue(nodeCountries);
        }

        return new Movie(nameNative + "/" + nameRussian, yearOfRelease, description, rating, price, picturePath, genres, countries);
    }
}
