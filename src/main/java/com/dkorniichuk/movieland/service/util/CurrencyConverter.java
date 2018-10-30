package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.entity.Movie;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyConverter {
    Logger logger = LoggerFactory.getLogger(CurrencyConverter.class);

    @Autowired
    private String nbuCurrencyRateURL;

    public Movie convertPrice(Movie movie, String currency) throws IOException {
        logger.info("Start currency conversion to " + currency);
        if (movie == null) {
            return null;
        }
        double rate = getCurrencyRate(currency);
        movie.setPrice(Math.round((movie.getPrice() / rate) * 100.0) / 100.0);
        return movie;
    }

    private double getCurrencyRate(String currency) throws IOException {
        logger.info("Connect to NBU api... ");
        URL url = new URL(nbuCurrencyRateURL + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        StringBuilder currencyInfo = getCurrencyInfo(connection);
        ObjectMapper mapper = new ObjectMapper();
        //TODO: change to readValue
        JsonNode currencyNode = mapper.readTree(currencyInfo.toString());
        //String rateA = mapper.readValue(currencyInfo.toString(),String.class);
        String rate = currencyNode.size() == 0 ? "1" : currencyNode.get(0).get("rate").asText();
        return Double.valueOf(rate);
    }

    private StringBuilder getCurrencyInfo(HttpURLConnection connection) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();
        return response;
    }
}
