package com.dkorniichuk.movieland.service;

import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;

import java.io.IOException;
import java.util.UUID;

public interface UserSecurityService {

    AuthenticationToken getAuthenticationToken(String userCredentials) throws IOException;

    void logout(UUID uuid);

    User findUserByTokenUuid(UUID uuid);


}
