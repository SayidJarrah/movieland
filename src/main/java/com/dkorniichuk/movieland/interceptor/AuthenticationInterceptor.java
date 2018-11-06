package com.dkorniichuk.movieland.interceptor;

import com.dkorniichuk.movieland.service.util.AuthenticationTokenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthenticationTokenCache cache;


    //TODO: need somehow handle userRole
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        logger.info("Interceptor");
        if ("GET".equals(request.getMethod())) {
            logger.info("Skip authentication");
            return true;
        }
        String uuid = request.getHeader("uuid");
        String userKey = cache.getUserKeyByUUID(UUID.fromString(uuid));
        if (userKey.isEmpty()) {
            response.getWriter().write("Authentication failed!");
            response.setStatus(403);
            return false;
        }
        logger.info("Successful authentication");
        return true;

    }
}
