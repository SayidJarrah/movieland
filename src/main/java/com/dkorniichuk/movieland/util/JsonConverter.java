package com.dkorniichuk.movieland.util;

import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.entity.Movie;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class JsonConverter {
    private final static String COLON_SEPARATOR = ":";
    private final static String COMMA_SEPARATOR = ",";

    public String toJSON(Movie movie) {
        StringBuilder json = new StringBuilder("{");
        String[] movieFieldNames = {"id", "nameRussian", "nameNative", "yearOfRelease", "description",
                "rating", "price", "picturePath", "countries", "genres"};
        Object[] movieFields = {movie.getId(), movie.getRussianName(), movie.getNativeName(), movie.getYearOfRelease(),
                movie.getDescription(), movie.getRating(), movie.getPrice(), movie.getPicturePath(),
                movie.getCountries(), movie.getGenres()};

        for (int i = 0; i < movieFieldNames.length; i++) {
            json.append(wrapByQuotes(movieFieldNames[i]));
            json.append(COLON_SEPARATOR);
            json.append(wrapByQuotes(movieFields[i]));
            if (i != movieFieldNames.length - 1) {
                json.append(COMMA_SEPARATOR);
            }
        }
        json.append("}");
        return json.toString();
    }
    //TODO:
    /*public JSONObject toJSON(Movie movie) {
        String[] movieFieldNames = {"id", "nameRussian", "nameNative", "yearOfRelease", "description",
                "rating", "price", "picturePath", "countries", "genres"};
        Object[] movieFields = {movie.getId(), movie.getRussianName(), movie.getNativeName(), movie.getYearOfRelease(),
                movie.getDescription(), movie.getRating(), movie.getPrice(), movie.getPicturePath(),
                movie.getCountries(), movie.getGenres()};
        JSONObject json = new JSONObject();

        for (int i = 0; i < movieFieldNames.length; i++) {
           json.put(movieFieldNames[i],movieFields[i]);
        }

        return json;
    }*/

    public String toJSON(Genre genre) {
        StringBuilder json = new StringBuilder("{");
        String[] genreFieldNames = {"id", "name"};
        Object[] genreFields = {genre.getId(), genre.getName()};

        for (int i = 0; i < genreFieldNames.length; i++) {
            json.append(wrapByQuotes(genreFieldNames[i]));
            json.append(COLON_SEPARATOR);
            json.append(wrapByQuotes(genreFields[i]));
            if (i != genreFieldNames.length - 1) {
                json.append(COMMA_SEPARATOR);
            }
        }
        json.append("}");
        return json.toString();
    }

    public Movie fromJSON(String json) {
        return null;
    }

    private String wrapByQuotes(Object value) {
        return !(value instanceof Set) ? ("\"" + value + "\"") : value.toString();

    }

}
