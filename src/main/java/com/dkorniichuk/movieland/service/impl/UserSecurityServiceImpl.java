package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;


    @Override
    public AuthenticationToken getAuthenticationToken(String userCredentials) throws IOException {
        logger.info("Start UserSecurity service.");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userCredentials, User.class);
        String emailFromRequest = user.getEmail();
        String passwordFromRequest = user.getPassword();

        User userFromDB = userDao.getUserByEmail(emailFromRequest);
        //TODO: saveAuthenticationTokenToCache()

        AuthenticationToken authenticationToken = null;
        if (userFromDB != null && passwordFromRequest.equals(userFromDB.getPassword())) {
            authenticationToken = createAuthenticationToken(userFromDB);
        }

        return authenticationToken;


    }

    private AuthenticationToken createAuthenticationToken(User user) {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUuid(UUID.randomUUID());
        authenticationToken.setNickname(user.getNickname());
        // authenticationToken.setExpireDate(LocalDateTime.now());
        return authenticationToken;
    }
}
