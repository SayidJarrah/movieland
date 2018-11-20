package com.dkorniichuk.movieland.dao.impl;

import com.dkorniichuk.movieland.dao.ReviewDao;
import com.dkorniichuk.movieland.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;

@Repository
public class ReviewDaoImpl implements ReviewDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource(name = "queryMap")
    private HashMap<String, String> queryMap;

    @Override
    public void addReview(Review review) {
        jdbcTemplate.update(queryMap.get("addReview"), new Object[]{review.getUser().getId(), review.getMovie().getId(), review.getText()});
    }
}
