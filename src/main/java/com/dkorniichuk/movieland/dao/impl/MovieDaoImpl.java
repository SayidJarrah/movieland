package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.MovieDao;
import com.dkorniichuk.movieland.dao.mapper.MovieResultSetExtractor;
import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Repository
public class MovieDaoImpl implements MovieDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queryMap")
    private HashMap<String, String> queryMap;

    @Override
    public List<Movie> getAllMovies() {
        logger.info("Start query to get all movies from DB");
        return jdbcTemplate.query(queryMap.get("getAllMovies"), new MovieResultSetExtractor());
    }

    @Override
    public List<Movie> getRandomMovies() {
        logger.info("Start query to get 3 random movies from DB");
        return jdbcTemplate.query(queryMap.get("getRandomMovies"), new MovieResultSetExtractor());
    }

    @Override
    public List<Movie> getMoviesByGenre(int id) {
        logger.info("Start query to get movies by genre");
        return jdbcTemplate.query(queryMap.get("getMoviesByGenre"), new Object[]{id}, new MovieResultSetExtractor());
    }

    @Override
    public Movie getMovieById(int id) {
        logger.info("Start query to get movie by id");
        //TODO: use query for object!
        List<Movie> result = jdbcTemplate.query(queryMap.get("getMovieById"), new Object[]{id}, new MovieResultSetExtractor());
        return !result.isEmpty() ? result.get(0) : null;
    }

    @Override
    public void addMovie(Movie movie) {
        logger.info("Start query to get add movie");
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(queryMap.get("addMovie"), Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, movie.getName());
                    ps.setString(2, movie.getPicturePath());
                    ps.setInt(3, movie.getYearOfRelease());
                    ps.setString(4, movie.getDescription());
                    ps.setDouble(5, movie.getRating());
                    ps.setDouble(6, movie.getPrice());
                    return ps;
                }
            }, keyHolder);
            logger.info("Added movie, id = {}", keyHolder.getKey().intValue());

            updateMovieHasGenre(keyHolder.getKey().intValue(), movie.getGenres());
            updateMovieHasCountry(keyHolder.getKey().intValue(), movie.getCountries());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    //TODO: rewrite with factory method pattern
    private void updateMovieHasGenre(int movieId, Set<Genre> genres) {
        List<Genre> genreList = new ArrayList<>();
        genreList.addAll(genres);
        jdbcTemplate.batchUpdate(queryMap.get("addToMovieHasGenre"), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Genre genre = genreList.get(i);
                ps.setInt(1, movieId);
                ps.setInt(2, genre.getId());
            }

            @Override
            public int getBatchSize() {
                return genres.size();
            }
        });
    }

    private void updateMovieHasCountry(int movieId, Set<Country> countries) {
        List<Country> countryList = new ArrayList<>();
        countryList.addAll(countries);
        jdbcTemplate.batchUpdate(queryMap.get("addToMovieHasCountry"), new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Country country = countryList.get(i);
                ps.setInt(1, movieId);
                ps.setInt(2, country.getId());
            }

            @Override
            public int getBatchSize() {
                return countries.size();
            }
        });
    }

    @Override
    public void editMovie(int id, Movie movie) {
        logger.info("Start query to edit movie id={}", id);
        jdbcTemplate.update(queryMap.get("cleanUpMovieHasGenreWhenEditMovie"), id);
        updateMovieHasGenre(id, movie.getGenres());
        jdbcTemplate.update(queryMap.get("cleanUpMovieHasCountryWhenEditMovie"), id);
        updateMovieHasCountry(id, movie.getCountries());
        jdbcTemplate.update(queryMap.get("editMovie"), new Object[]{movie.getName(), movie.getPicturePath(), movie.getYearOfRelease(), movie.getDescription(),
                movie.getRating(), movie.getPrice(), id});
    }


    @Override
    public void rateMovie(int movieId, double rating, int userId) {
        logger.info("Start query to rate movie id={}, rate={}, userId={}", movieId, rating, userId);
        int count = jdbcTemplate.queryForObject(queryMap.get("isRatedByCurrentUser"), new Object[]{movieId, userId}, Integer.class);

        if (count == 0) {
            jdbcTemplate.update(queryMap.get("addRate"), rating, userId, movieId);
        } else {
            jdbcTemplate.update(queryMap.get("updateOwnRate"), rating, movieId, userId);
        }
        jdbcTemplate.update(queryMap.get("updateTotalRate"), movieId);
    }

    @Override
    public Double getOwnRatingForMovie(int id, int userId) {
        logger.info("Start query to get movie rating id={}", id, userId);
        return jdbcTemplate.queryForObject(queryMap.get("getOwnRatingForMovie"),
                new Object[]{id, userId}, Double.class);
    }

}
