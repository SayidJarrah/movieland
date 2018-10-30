package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.entity.Movie;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SortingHelper {
    private final static String ASCENDING_TYPE = "asc";
    private final static String RATING_SORT = "rating";

    public static List<Movie> sort(List<Movie> movies, Map<String, String> sortedParams) {

        Map.Entry<String, String> entry = sortedParams.entrySet().stream().findAny().get();
        return RATING_SORT.equalsIgnoreCase(entry.getKey()) ?
                sortByRating(movies, entry.getValue()) : sortByPrice(movies, entry.getValue());
    }

    private static List<Movie> sortByRating(List<Movie> movies, String type) {
        return ASCENDING_TYPE.equalsIgnoreCase(type) ? movies.stream().sorted(
                (movie1, movie2) ->
                        new Double(movie1.getRating()).compareTo(movie2.getRating()))
                .collect(Collectors.toList()) : movies.stream().sorted(
                (movie1, movie2) ->
                        new Double(movie2.getRating()).compareTo(movie1.getRating()))
                .collect(Collectors.toList());
    }

    private static List<Movie> sortByPrice(List<Movie> movies, String type) {
        return ASCENDING_TYPE.equalsIgnoreCase(type) ? movies.stream().sorted(
                (movie1, movie2) ->
                        new Double(movie1.getPrice()).compareTo(movie2.getPrice()))
                .collect(Collectors.toList()) : movies.stream().sorted(
                (movie1, movie2) ->
                        new Double(movie2.getPrice()).compareTo(movie1.getPrice()))
                .collect(Collectors.toList());
    }
}
