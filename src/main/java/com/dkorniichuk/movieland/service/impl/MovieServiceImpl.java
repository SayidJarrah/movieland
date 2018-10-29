package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    public List<Movie> getAllMovies() {
        return movieDao.getAllMovies();
    }

    public List<Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    public List<Movie> getMoviesByGenre(int id) {
        return movieDao.getMoviesByGenre(id);
    }

    @Override
    public List<Movie> getAllMoviesSortedByRating(String rating) {
        return "ASC".equalsIgnoreCase(rating) ? movieDao.getAllMovies().stream().sorted(
                (movie1, movie2) ->
                        new Double(movie1.getRating()).compareTo(movie2.getRating()))
                .collect(Collectors.toList()) : movieDao.getAllMovies().stream().sorted(
                (movie1, movie2) ->
                        new Double(movie2.getRating()).compareTo(movie1.getRating()))
                .collect(Collectors.toList());
    }


}
