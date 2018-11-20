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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Repository
public class MovieDaoImpl implements MovieDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getAllMovies;

    @Autowired
    private String getRandomMovies;

    @Autowired
    private String getMoviesByGenre;

    @Autowired
    private String getMovieById;

    @Autowired
    private String addMovie;

    @Autowired
    private String addToMovieHasGenre;

    @Autowired
    private String addToMovieHasCountry;
    @Autowired
    private String editMovie;

    @Override
    public List<Movie> getAllMovies() {
        logger.info("Start query to get all movies from DB");
        return jdbcTemplate.query(getAllMovies, new MovieResultSetExtractor());
    }

    @Override
    public List<Movie> getRandomMovies() {
        logger.info("Start query to get 3 random movies from DB");
        return jdbcTemplate.query(getRandomMovies, new MovieResultSetExtractor());
    }

    @Override
    public List<Movie> getMoviesByGenre(int id) {
        logger.info("Start query to get movies by genre");
        return jdbcTemplate.query(getMoviesByGenre, new Object[]{id}, new MovieResultSetExtractor());
    }

    @Override
    public Movie getMovieById(int id) {
        logger.info("Start query to get movie by id");
        List<Movie> result = jdbcTemplate.query(getMovieById, new Object[]{id}, new MovieResultSetExtractor());
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
                    PreparedStatement ps = connection.prepareStatement(addMovie, Statement.RETURN_GENERATED_KEYS);
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
        jdbcTemplate.batchUpdate(addToMovieHasGenre, new BatchPreparedStatementSetter() {
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
        jdbcTemplate.batchUpdate(addToMovieHasCountry, new BatchPreparedStatementSetter() {
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

    //TODO: SQL queries to configuration
    @Override
    public void editMovie(int id, Movie movie) {
        logger.info("Start query to edit movie id={}", id);
        jdbcTemplate.update("DELETE FROM movie_has_genre WHERE movie_id = ?", id);
        updateMovieHasGenre(id, movie.getGenres());
        jdbcTemplate.update("DELETE FROM movie_has_country WHERE movie_id = ?", id);
        updateMovieHasCountry(id, movie.getCountries());
        jdbcTemplate.update(editMovie, new Object[]{movie.getName(), movie.getPicturePath(), movie.getYearOfRelease(), movie.getDescription(),
                movie.getRating(), movie.getPrice(), id});
    }


    @Override
    public void rateMovie(int movieId, double rating, int userId) {
        logger.info("Start query to rate movie id={}", movieId);
        jdbcTemplate.update("UPDATE rate SET rating = ? WHERE movie_id = ? AND user_id = ?", rating, movieId, userId);
        //TODO : update average rating

        jdbcTemplate.update("update movie set rating = (select sum(rating)/count(rating) from `mydb`.`rate`) where id=?",movieId);
    }


}
