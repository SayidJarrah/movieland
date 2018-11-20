package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.dao.mapper.UserRowMapper;
import com.dkorniichuk.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;

@Repository
public class UserDaoImpl implements UserDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queryMap")
    private HashMap<String, String> queryMap;

    @Override
    public User getUserByEmail(String email) {
        logger.info("Start query to get user by email: " + email);
        User user;
        try {
            user = jdbcTemplate.queryForObject(queryMap.get("getUserByEmail"), new Object[]{email}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            logger.error("Can't find user with such email");
            return null;
        }
        return user;
    }
}
