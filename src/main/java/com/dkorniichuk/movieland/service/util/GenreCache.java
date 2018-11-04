package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.dao.GenreDao;
import com.dkorniichuk.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GenreCache {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreDao genreDao;

    private List<Genre> genres;

    public GenreCache() {
    }

    @PostConstruct
    private void updateCache() {
        logger.info("GenreCache created");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            logger.info("GenreCache updated ");
            genres = genreDao.getAllGenres();
            logger.info(genres.toString());

        }, 0, 5, TimeUnit.MINUTES);
    }


    public List<Genre> getGenres() {
        return genres;
    }

}
