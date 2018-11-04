package com.dkorniichuk.movieland.vo;

import com.dkorniichuk.movieland.entity.Review;

public class ReviewVO {
    public String text;
    public int movieId;
    public int userId;

    public ReviewVO() {
    }

    public ReviewVO(Review review) {
        this.text = review.getText();
        this.movieId = review.getMovie().getId();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ReviewVO{" +
                "text='" + text + '\'' +
                ", movieId=" + movieId +
                ", userId=" + userId +
                '}';
    }
}


