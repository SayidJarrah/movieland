package com.dkorniichuk.movieland.dao;

import com.dkorniichuk.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(int id);
}
