package com.dkorniichuk.movieland.dao;

import com.dkorniichuk.movieland.entity.Review;

public interface ReviewDao {

    void addReview(Review review);

    Review getReview(int id);

    void removeReview(int id);
}
