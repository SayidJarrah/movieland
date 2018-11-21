package com.dkorniichuk.movieland.service;

import com.dkorniichuk.movieland.dao.util.Page;
import com.dkorniichuk.movieland.entity.Movie;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface MovieService {

    List<Movie> getAllMovies(Map<String, String> sortedParams);

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(int id, Map<String, String> sortedParams);

    Movie getMovieById(int id, String currency) throws IOException;

    void addMovie(String movieData) throws IOException;

    void editMovie(int id, String movieData) throws IOException;

    void rateMovie(int id, String rateData, UUID uuid) throws IOException;

    Double getOwnRatingForMovie(int id, UUID uuid) throws IOException;

    Page<Movie> search(String title, Integer page);

}
