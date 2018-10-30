package com.dkorniichuk.movieland.service.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CurrencyRateService {
    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json&valcode=";

    //TODO: refactor!!!
    public double sendGet(String currency) throws IOException {
        URL url = new URL(URL + currency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine = null;
        StringBuilder response = new StringBuilder();
        while ((inputLine = reader.readLine()) != null) {
            response.append(inputLine);
        }
        reader.close();

        System.out.println(response.toString());

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper);
        JsonNode currencyNode = mapper.readTree(response.toString());
        System.out.println(currencyNode);
        String rate = currencyNode.get(0).get("rate").asText();
        System.out.println(rate);
        return Double.valueOf(rate);

    }
}
