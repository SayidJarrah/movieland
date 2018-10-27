package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.GenreDao;
import com.dkorniichuk.movieland.entity.Genre;
import com.dkorniichuk.movieland.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreServiceImpl implements GenreService {
    @Autowired
    private GenreDao genreDao;

    public List<Genre> getAllGenres() {
        return genreDao.getAllGenres();
    }
}
