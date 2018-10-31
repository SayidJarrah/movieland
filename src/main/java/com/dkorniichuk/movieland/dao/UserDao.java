package com.dkorniichuk.movieland.dao;

import com.dkorniichuk.movieland.entity.User;

public interface UserDao {

    User getUserByEmail(String email);
}
