package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.dao.mapper.MovieResultSetExtractor;
import com.dkorniichuk.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDaoImpl implements MovieDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getAllMovies;

    @Autowired
    private String getRandomMovies;

    public List<Movie> getAllMovies() {
        logger.info("Start query to get all movies from DB");
        return jdbcTemplate.query(getAllMovies, new MovieResultSetExtractor());
    }

    public List<Movie> getRandomMovies() {
        logger.info("Start query to get 3 random movies from DB");
        return jdbcTemplate.query(getRandomMovies, new MovieResultSetExtractor());
    }


}
