package com.dkorniichuk.movieland.service.impl;

import com.dkorniichuk.movieland.dao.UserDao;
import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.entity.UserRole;
import com.dkorniichuk.movieland.service.UserSecurityService;
import com.dkorniichuk.movieland.service.util.AuthenticationTokenCache;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Service
public class UserSecurityServiceImpl implements UserSecurityService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthenticationTokenCache cache;


    @Override
    public AuthenticationToken getAuthenticationToken(String userCredentials) throws IOException {
        logger.info("Start UserSecurity service.");
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userCredentials, User.class);
        String emailFromRequest = user.getEmail();
        String passwordFromRequest = user.getPassword();

        User userFromDB = userDao.getUserByEmail(emailFromRequest);

        AuthenticationToken authenticationToken = null;
        if (userFromDB != null && passwordFromRequest.equals(userFromDB.getPassword())) {
            authenticationToken = createAuthenticationToken(userFromDB);
            saveAuthenticationTokenInCache(authenticationToken);
        }

        return authenticationToken;

    }


    @Override
    public void logout(UUID uuid) {
        logger.info("Start removing token uuid=" + uuid);
        cache.removeByUUID(uuid);
    }



    @Override
    public User findUserByTokenUuid(UUID uuid) {
        String email = cache.getUserKeyByUUID(uuid);
        return userDao.getUserByEmail(email);
    }

    private AuthenticationToken createAuthenticationToken(User user) {
        AuthenticationToken authenticationToken = new AuthenticationToken();
        authenticationToken.setUuid(UUID.randomUUID());
        authenticationToken.setEmail(user.getEmail());
        authenticationToken.setNickname(user.getNickname());
        authenticationToken.setUserRole(UserRole.getByValue(user.getUserTypeId()));
        return authenticationToken;
    }

    private void saveAuthenticationTokenInCache(AuthenticationToken token) {
        cache.add(token.getEmail(), token, 300000);
    }
}
