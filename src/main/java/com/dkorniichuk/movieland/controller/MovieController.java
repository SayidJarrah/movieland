package com.dkorniichuk.movieland.controller;


import com.dkorniichuk.movieland.dao.util.Page;
import com.dkorniichuk.movieland.entity.Movie;
import com.dkorniichuk.movieland.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/v1/movie")
public class MovieController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MovieService movieService;


    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getAllMovies(@RequestParam(required = false) Map<String, String> sortedParams) {
        logger.info("Sending request to get all movies");
        logger.info(sortedParams.toString());
        return movieService.getAllMovies(sortedParams);
    }

    @RequestMapping(value = "/random", method = RequestMethod.GET)
    @ResponseBody
    public List<Movie> getRandomMovies() {
        logger.info("Sending request to get 3 random movies");
        return movieService.getRandomMovies();
    }

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Movie> getMoviesByGenre(@PathVariable int id,
                                        @RequestParam(required = false) Map<String, String> sortedParams) {
        logger.info("Sending request to get movies by genre");
        return movieService.getMoviesByGenre(id, sortedParams);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Movie getMovieById(@PathVariable int id,
                              @RequestParam(required = false) String currency) throws IOException {
        logger.info("Sending request to get movies by id. Currency: " + (currency == null ? "UAH" : currency));
        return movieService.getMovieById(id, currency);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> addMovie(@RequestBody String movieData) throws IOException {
        logger.info("Sending request to add new movie {}", movieData);
        movieService.addMovie(movieData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> editMovie(@PathVariable int id,
                                                @RequestBody String movieData) throws IOException {
        logger.info("Sending request to edit movie");
        movieService.editMovie(id, movieData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/rate", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<HttpStatus> rateMovie(@PathVariable int id,
                                                @RequestBody String rateData,
                                                @RequestHeader String uuid) throws IOException {
        logger.info("Sending request to rate movie: id ={}", id);
        movieService.rateMovie(id, rateData, UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/rating", method = RequestMethod.GET)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_USER')")
    public Double getOwnRatingForMovie(@PathVariable int id,
                                       @RequestHeader String uuid) throws IOException {
        logger.info("Sending request to get own rating for movie id={}", id);
        return movieService.getOwnRatingForMovie(id, UUID.fromString(uuid));
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Page<Movie> search(@RequestParam String title,
                              @RequestParam(required = false) Integer page) {
        logger.info("Sending request search movie by title ={}", title, page);
        return movieService.search(title, page);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> markForRemoving(@PathVariable int id) {
        logger.info("Mark movie id={} for removing", id);
        movieService.markForRemoving(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}/unmark", method = RequestMethod.POST)
    @ResponseBody
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<HttpStatus> uncheckRemoving(@PathVariable int id) {
        logger.info("Uncheck movie id={} from removing", id);
        movieService.uncheckRemoving(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
