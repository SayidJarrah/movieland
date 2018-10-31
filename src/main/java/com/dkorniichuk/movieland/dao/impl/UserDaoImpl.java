package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.dao.mapper.UserRowMapper;
import com.dkorniichuk.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private String getUserByEmail;

    @Override
    public User getUserByEmail(String email) {
        logger.info("Start query to get user by email: " + email);
        //TODO: add check when email not exists
        return jdbcTemplate.queryForObject(getUserByEmail, new Object[]{email}, new UserRowMapper());
    }
}
