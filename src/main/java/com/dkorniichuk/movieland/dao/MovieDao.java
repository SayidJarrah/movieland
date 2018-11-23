package com.dkorniichuk.movieland.dao;

import com.dkorniichuk.movieland.dao.util.Page;
import com.dkorniichuk.movieland.entity.Movie;

import java.util.List;

public interface MovieDao {

    List<Movie> getAllMovies();

    List<Movie> getRandomMovies();

    List<Movie> getMoviesByGenre(int id);

    Movie getMovieById(int id);

    void addMovie(Movie movie);

    void editMovie(int id, Movie movie);

    void rateMovie(int id, double rating, int userId);

    Double getOwnRatingForMovie(int id, int userId);

    Page<Movie> search(String title, Integer page);

    void markForRemoving(int id);

    void uncheckRemoving(int id);
}
