package com.dkorniichuk.movieland.service;

import com.dkorniichuk.movieland.entity.Movie;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MovieService {

    List<Movie> getAllMovies(Map<String, String> sortedParams);

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(int id, Map<String, String> sortedParams);

    Movie getMovieById(int id);

}
