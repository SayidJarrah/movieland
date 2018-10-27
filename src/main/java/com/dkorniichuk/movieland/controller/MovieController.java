package com.dkorniichuk.movieland.controller;


import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import com.dkorniichuk.movieland.util.JsonConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;

    @Autowired
    private JsonConverter jsonConverter;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAllMovies() {
        logger.info("Sending request to get all movies");
        List<String> response = new ArrayList<String>();
        List<Movie> movies = movieService.getAllMovies();

        for (Movie movie : movies) {
            response.add(jsonConverter.toJSON(movie));
        }

        return response;
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getRandomMovies() {
        logger.info("Sending request to get 3 random movies");
        List<String> response = new ArrayList<String>();
        List<Movie> movies = movieService.getRandomMovies();

        for (Movie movie : movies) {
            response.add(jsonConverter.toJSON(movie));
        }

        return response;
    }


}
