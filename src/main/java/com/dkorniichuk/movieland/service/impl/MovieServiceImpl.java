package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import com.dkorniichuk.movieland.service.util.SortingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDao movieDao;

    @Override
    public List<Movie> getAllMovies(Map<String, String> sortedParams) {
        return sortedParams.isEmpty() ? movieDao.getAllMovies() : SortingHelper.sort(movieDao.getAllMovies(), sortedParams);
    }

    @Override
    public List<Movie> getRandomMovies() {
        return movieDao.getRandomMovies();
    }

    @Override
    public List<Movie> getMoviesByGenre(int id, Map<String, String> sortedParams) {
        return sortedParams.isEmpty() ? movieDao.getMoviesByGenre(id) : SortingHelper.sort(movieDao.getMoviesByGenre(id), sortedParams);
    }

    @Override
    public Movie getMovieById(int id) {
        return movieDao.getMovieById(id);
    }

}
