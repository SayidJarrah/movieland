package com.dkorniichuk.movieland.security;

import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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
        //TODO: refactor this code
        if ("null".equals(uuid)) {
            User anonymousUser = new User();
            anonymousUser.setUserTypeId(3);
            anonymousUser.getAuthorities();
            return anonymousUser;
        }
        return Optional.ofNullable(userSecurityService.findUserByTokenUuid(UUID.fromString(uuid)))
                .orElseThrow(() -> new UsernameNotFoundException("Cannot find user with authentication token " + token));
    }
}
