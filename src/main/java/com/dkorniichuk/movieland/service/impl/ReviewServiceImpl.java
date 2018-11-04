package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.ReviewDao;
import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import com.dkorniichuk.movieland.service.util.AuthenticationTokenCache;
import com.dkorniichuk.movieland.vo.ReviewVO;
import com.dkorniichuk.movieland.service.ReviewService;
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
        ReviewVO review = mapper.readValue(reviewStr, ReviewVO.class);
        String userKey = cache.getUserKeyByUUID(uuid);

        //TODO: refactor this code!!!
        User user = userDao.getUserByEmail(userKey);
        review.setUserId(user.getId());
        //TODO: troubles with constraints -review!!!
        reviewDao.addReview(review);
        System.out.println(user);

    }
}
