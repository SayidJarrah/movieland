package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.dao.MovieDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class MovieCleanUpAutoTask {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MovieDao movieDao;

    @PostConstruct
    private void removeMarkedMovies() {

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime midnightDate = currentTime.plusDays(1).toLocalDate().atStartOfDay();
        long initialDelay = ChronoUnit.MINUTES.between(currentTime, midnightDate);
        logger.info("Initial delay = {} ", initialDelay);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            logger.info("Marked movies removed at {}", LocalDateTime.now());
            movieDao.deleteMarkedMovie();
        }, initialDelay, 1440, TimeUnit.SECONDS); //TODO: seconds for testing purposes, should be MINUTES
    }


}
