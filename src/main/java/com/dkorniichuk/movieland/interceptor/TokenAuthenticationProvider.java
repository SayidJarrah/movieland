package com.dkorniichuk.movieland.interceptor;

import com.dkorniichuk.movieland.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    @Autowired
    private UserSecurityService userSecurityService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String s, UsernamePasswordAuthenticationToken authenticationToken) throws AuthenticationException {
        final Object token = authenticationToken.getCredentials();
        String uuid = String.valueOf(token);
        return userSecurityService.getUserByUUID(UUID.fromString(uuid));
    }
}
