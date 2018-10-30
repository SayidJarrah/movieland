package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.entity.Movie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class CurrencyConverter {
    static Logger logger = LoggerFactory.getLogger(CurrencyConverter.class);

    @Autowired
    CurrencyRateService currencyRateService;
//TODO:refactor
    public Movie convertPrice(Movie movie, String currency) throws IOException {
        logger.info("Start currency conversion to " + currency);
        double rate = currencyRateService.sendGet(currency);
        movie.setPrice(Math.round((movie.getPrice() / rate) * 100.0) / 100.0);
        return movie;
    }
}
