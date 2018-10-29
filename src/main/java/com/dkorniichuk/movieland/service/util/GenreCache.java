package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.dao.GenreDao;
import com.dkorniichuk.movieland.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class GenreCache {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private GenreDao genreDao;

     private static GenreCache instance;
    private List<Genre> genres;

    public GenreCache() {
        logger.info("GenreCache created");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                logger.info("GenreCache updated ");
                genres = genreDao.getAllGenres();
                logger.info(genres.toString());
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

  /*  public static GenreCache getInstance() {
        if (instance == null) {
            instance = new GenreCache();
        }
        return instance;
    }*/


    public List<Genre> getGenres() {
        return genres;
    }

  /*  public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }*/
}
