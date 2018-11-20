package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.CountryDao;
import com.dkorniichuk.movieland.dao.mapper.CountryRowMapper;
import com.dkorniichuk.movieland.entity.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository
public class CountryDaoImpl implements CountryDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queryMap")
    private HashMap<String, String> queryMap;

    @Override
    public List<Country> getAllCountries() {
        logger.info("Start query to get all countries from DB");
        return jdbcTemplate.query(queryMap.get("getAllCountries"), new CountryRowMapper());
    }
}
