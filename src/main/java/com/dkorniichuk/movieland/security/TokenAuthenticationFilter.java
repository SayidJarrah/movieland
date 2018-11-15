package com.dkorniichuk.movieland.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    protected TokenAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager/*, String excludedUrl*/) {
        super(defaultFilterProcessesUrl);
        super.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(defaultFilterProcessesUrl));
        setAuthenticationManager(authenticationManager);
        setAuthenticationSuccessHandler(new TokenAuthenticationSuccessHandler());
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String uuid = request.getHeader("uuid");
        Authentication auth = new UsernamePasswordAuthenticationToken(uuid, uuid);
        return getAuthenticationManager().authenticate(auth);
    }

}