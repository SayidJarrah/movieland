package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value="/v1/country")
public class CountryController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CountryService countryService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Country> getCountries(){
        logger.info("Sending request to get all countries");
        return countryService.getAllCountries();
    }
}
