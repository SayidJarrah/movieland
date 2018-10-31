package com.dkorniichuk.movieland.service.util;

import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationTokenCache {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static AuthenticationTokenCache instance;
    private Map<User, AuthenticationToken> userAuthenticationTokenMap;

    private AuthenticationTokenCache() {
    }    ;


    public static AuthenticationTokenCache getInstance() {
        if (instance == null) {
            instance = new AuthenticationTokenCache();
        }
        return instance;
    }

    public Map<User, AuthenticationToken> getUserAuthenticationTokenMap() {
        return userAuthenticationTokenMap;
    }

    public void setUserAuthenticationTokenMap(Map<User, AuthenticationToken> userAuthenticationTokenMap) {
        this.userAuthenticationTokenMap = userAuthenticationTokenMap;
    }
}
