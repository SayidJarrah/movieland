package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import com.dkorniichuk.movieland.service.util.CurrencyConverter;
import com.dkorniichuk.movieland.service.util.SortingHelper;
import com.dkorniichuk.movieland.vo.MovieVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@Service
public class MovieServiceImpl implements MovieService {

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
    public void addMovie(String movieData, String uuid){
        ObjectMapper objectMapper = new ObjectMapper();
        MovieVO movie = null;
        try {
            movie = objectMapper.readValue(movieData, MovieVO.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(movie);
    }

    @Override
    public void editMovie(String moviedData, String uuid) {

    }

}
