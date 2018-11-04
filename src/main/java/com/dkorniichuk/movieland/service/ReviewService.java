package com.dkorniichuk.movieland.service;

import java.io.IOException;
import java.util.UUID;

public interface ReviewService {

    void addReview(String reviewStr, UUID uuid) throws IOException;
}
