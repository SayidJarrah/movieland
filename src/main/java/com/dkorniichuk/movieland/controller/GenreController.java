package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/v1/genre")
public class GenreController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Genre> getAllGenres() {
        logger.info("Sending request to get all genres");
        return genreService.getAllGenres();
    }

}
