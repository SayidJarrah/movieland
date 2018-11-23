package com.dkorniichuk.movieland.service.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


//TODO: need to implement!
@Service
public class MovieCleanUpAutoTask {
    Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    private void removeMarkedMovies() {
        logger.info("Cleanup auto task started");
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime midnightDate = currentTime.plusDays(1).toLocalDate().atStartOfDay();

        long l = ChronoUnit.MINUTES.between(currentTime,midnightDate);

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("deleted marked movies");

        }, 0, l, TimeUnit.MINUTES);
    }


}
