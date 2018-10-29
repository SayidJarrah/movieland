package com.dkorniichuk.movieland.controller;


import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getAllMovies() {
        logger.info("Sending request to get all movies");
        return movieService.getAllMovies();
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getRandomMovies() {
        logger.info("Sending request to get 3 random movies");
        return movieService.getRandomMovies();
    }

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Movie> getMoviesByGenre(@PathVariable int id) {
        logger.info("Sending request to get movies by genre");
        return movieService.getMoviesByGenre(id);
    }


}
