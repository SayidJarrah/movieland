package com.dkorniichuk.movieland.dao.mapper;

import com.dkorniichuk.movieland.entity.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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
            if (doesColumnExists("review_id", resultSet)) {
                extractReviews(movie, resultSet);
            }


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

    private void extractReviews(Movie movie, ResultSet resultSet) throws SQLException {
        Set<Review> reviews = movie.getReviews();
        if (reviews == null) {
            reviews = new HashSet<>();
        }
        Review review = new Review();
        review.setId(resultSet.getInt("review_id"));
        review.setText(resultSet.getString("text"));
        User user = new User();
        user.setId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        review.setUser(user);
        reviews.add(review);
        movie.setReviews(reviews);
    }

    public static boolean doesColumnExists(String columnName, ResultSet resultSet) throws SQLException {
        ResultSetMetaData meta = resultSet.getMetaData();
        int numCol = meta.getColumnCount();
        for (int i = 1; i <= numCol; i++) {
            if (meta.getColumnLabel(i).toString().equalsIgnoreCase(columnName)) {
                return true;
            }
        }
        return false;
    }
}
