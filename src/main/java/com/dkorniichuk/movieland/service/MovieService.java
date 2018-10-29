package com.dkorniichuk.movieland.service;

import com.dkorniichuk.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();
    List<Movie> getRandomMovies();
    List<Movie> getMoviesByGenre(int genreId);
    List<Movie> getAllMoviesSortedByRating(String rating);
}
