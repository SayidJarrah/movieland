package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.GenreDao;
import com.dkorniichuk.movieland.dao.mapper.GenreRowMapper;
import com.dkorniichuk.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Repository
public class GenreDaoImpl implements GenreDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queryMap")
    private HashMap<String, String> queryMap;

    public List<Genre> getAllGenres() {
        logger.info("Start query to get all genres from DB");
        return jdbcTemplate.query(queryMap.get("getAllGenres"), new GenreRowMapper());
    }
}
