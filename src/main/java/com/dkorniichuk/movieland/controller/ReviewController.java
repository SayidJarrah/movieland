package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.service.ReviewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
public class ReviewController {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ReviewService reviewService;
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/v1/review", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<HttpStatus> addReview(
            @AuthenticationPrincipal @RequestBody String review,
            @RequestHeader String uuid) throws IOException {
        logger.info("Sending request to add movie review");
        reviewService.addReview(review, UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
