package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import com.dkorniichuk.movieland.service.util.CurrencyConverter;
import com.dkorniichuk.movieland.service.util.SortingHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class MovieServiceImpl implements MovieService {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private MovieDao movieDao;

    @Autowired
    private CurrencyConverter converter;

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

    //TODO:refactor
    @Override
    public Movie getMovieById(int id, String currency) throws IOException {
        return currency == null ? movieDao.getMovieById(id) : converter.convertPrice(movieDao.getMovieById(id), currency);
    }

    @Override
    public void addMovie(String movieData, String uuid) throws IOException {
        logger.info("Add movie service started...");
        ObjectMapper objectMapper = new ObjectMapper();
        Movie movie = objectMapper.readValue(movieData, Movie.class);
        movieDao.addMovie(movie);
    }

    @Override
    public void editMovie(String movieData, String uuid) {
        logger.info("Edit movie service started...");
    }

}
