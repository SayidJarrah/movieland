package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.ReviewVO;
import com.dkorniichuk.movieland.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ReviewServiceImpl implements ReviewService {
    @Override
    public void addReview(String reviewStr) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ReviewVO review = mapper.readValue(reviewStr, ReviewVO.class);
        System.out.println(review);
    }
}
