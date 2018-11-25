package com.dkorniichuk.movieland.dao.mapper;

import com.dkorniichuk.movieland.entity.Review;
import com.dkorniichuk.movieland.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {
    @Override
    public Review mapRow(ResultSet resultSet, int i) throws SQLException {
        Review review = new Review();
        review.setId(resultSet.getInt("id"));
        review.setText(resultSet.getString("text"));
        User author = new User();
        author.setId(resultSet.getInt("user_id"));
        author.setUserTypeId(resultSet.getInt("role"));
        author.setEmail(resultSet.getString("email"));
        review.setUser(author);
        return review;
    }
}
