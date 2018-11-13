package com.dkorniichuk.movieland.interceptor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private String excludedUrl;

    protected TokenAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager, String excludedUrl) {
        super(defaultFilterProcessesUrl);
        this.excludedUrl = excludedUrl;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String uuid = request.getHeader("uuid");


        final Authentication auth = new UsernamePasswordAuthenticationToken(uuid, uuid);

        return getAuthenticationManager().authenticate(auth);
    }

    @Override
    protected void successfulAuthentication(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain,
            final Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        chain.doFilter(request, response);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        if (((HttpServletRequest) req).getRequestURI().endsWith(excludedUrl)) {
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
        }
    }
}