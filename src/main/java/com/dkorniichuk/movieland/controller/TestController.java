package com.dkorniichuk.movieland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class TestController {

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getAllMovies() {
        ModelAndView model = new ModelAndView("index");
        return model;
    }


}
