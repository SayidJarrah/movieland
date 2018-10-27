package com.dkorniichuk.movieland.service;

import com.dkorniichuk.movieland.entity.Movie;

import java.util.List;

public interface MovieService {

    List<Movie> getAllMovies();
    List<Movie> getRandomMovies();
}
