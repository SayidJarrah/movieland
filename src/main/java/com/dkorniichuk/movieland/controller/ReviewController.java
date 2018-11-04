package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReviewService reviewService;

    @RequestMapping(value = "/v1/review", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<HttpStatus> addReview(@RequestBody String review) throws IOException {
        logger.info("Sending request to add movie review");
        reviewService.addReview(review);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
