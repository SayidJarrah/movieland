package com.dkorniichuk.movieland.interceptor;

import com.dkorniichuk.movieland.service.util.AuthenticationTokenCache;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

public class MDCLoggerInterceptor extends HandlerInterceptorAdapter {
    private final String GUEST = "guest";
    private final String USER_KEY = "username";
    private final String REQUEST_ID_KEY = "requestId";
    @Autowired
    private AuthenticationTokenCache cache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String uuid = request.getHeader("uuid");
        MDC.put(USER_KEY, uuid == null ? GUEST : cache.getUserKeyByUUID(UUID.fromString(uuid)));
        MDC.put(REQUEST_ID_KEY, UUID.randomUUID().toString());
        return true;
    }
}