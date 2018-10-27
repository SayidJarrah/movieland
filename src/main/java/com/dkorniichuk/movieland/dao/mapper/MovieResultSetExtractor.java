package com.dkorniichuk.movieland.dao.mapper;

import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.entity.Movie;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MovieResultSetExtractor implements ResultSetExtractor<List<Movie>> {
    public List<Movie> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        Map<Integer, Movie> map = new HashMap<Integer, Movie>();

        while (resultSet.next()) {
            Integer id = resultSet.getInt("id");
            Movie movie = map.get(id);
            if (movie == null) {
                movie = new Movie();
                movie.setId(resultSet.getInt("id"));
                movie.setName(resultSet.getString("name"));
                movie.setYearOfRelease(resultSet.getInt("year"));
                movie.setDescription(resultSet.getString("description"));
                movie.setRating(resultSet.getDouble("rating"));
                movie.setPrice(resultSet.getDouble("price"));
                movie.setPicturePath(resultSet.getString("poster"));
                map.put(id, movie);
            }

            extractGenres(movie, resultSet);
            extractCountries(movie, resultSet);

        }

        return new ArrayList<Movie>(map.values());
    }

    private void extractGenres(Movie movie, ResultSet resultSet) throws SQLException {
        Set<Genre> genres = movie.getGenres();
        if (genres == null) {
            genres = new HashSet();
        }
        Genre genre = new Genre();
        genre.setId(resultSet.getInt("genre_id"));
        genre.setName(resultSet.getString("genre"));
        genres.add(genre);
        movie.setGenres(genres);
    }

    private void extractCountries(Movie movie, ResultSet resultSet) throws SQLException {
        Set<Country> countries = movie.getCountries();
        if (countries == null) {
            countries = new HashSet();
        }
        Country country = new Country();
        country.setId(resultSet.getInt("country_id"));
        country.setName(resultSet.getString("country"));
        countries.add(country);
        movie.setCountries(countries);
    }
}
