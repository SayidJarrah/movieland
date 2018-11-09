package com.dkorniichuk.movieland.interceptor;

import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("TokenAuthenticationProvider");
        final Object token = authenticationToken.getCredentials();
        String uuid = String.valueOf(token);
        User user = userSecurityService.findUserByTokenUuid(UUID.fromString(uuid));
        return userSecurityService.findUserByTokenUuid(UUID.fromString(uuid));
    }
}
