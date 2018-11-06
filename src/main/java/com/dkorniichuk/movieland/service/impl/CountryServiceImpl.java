package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.CountryDao;
import com.dkorniichuk.movieland.entity.Country;
import com.dkorniichuk.movieland.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    @Autowired
    private CountryDao countryDao;

    @Override
    public List<Country> getAllCountries() {
        return countryDao.getAllCountries();
    }
}
