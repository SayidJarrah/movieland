package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.ReviewDao;
import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.entity.Review;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.entity.UserRole;
import com.dkorniichuk.movieland.service.ReviewService;
import com.dkorniichuk.movieland.service.util.AuthenticationTokenCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private AuthenticationTokenCache cache;
    @Autowired
    private UserDao userDao;
    @Autowired
    private ReviewDao reviewDao;

    @Override
    public void addReview(String reviewStr, UUID uuid) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Review review = mapper.readValue(reviewStr, Review.class);
        String userKey = cache.getUserKeyByUUID(uuid);

        User user = userDao.getUserByEmail(userKey);
        review.setUser(user);
        reviewDao.addReview(review);
        System.out.println(user);

    }

    @Override
    public void removeReview(int id, UUID uuid) {

        String userKey = cache.getUserKeyByUUID(uuid);
        User user = userDao.getUserByEmail(userKey);
        if (UserRole.ROLE_ADMIN.getValue() == user.getUserTypeId()) {
            reviewDao.removeReview(id);
        } else {
            Review review = reviewDao.getReview(id);
            if (review.getUser().getId() == user.getId()) {
                reviewDao.removeReview(id);
            }
        }
    }
}
