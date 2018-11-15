package com.dkorniichuk.movieland.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Override
    protected String determineTargetUrl(HttpServletRequest request,
                                        HttpServletResponse response) {
        String context = request.getContextPath();
        String fullURL = request.getRequestURI();
        String url = fullURL.substring(fullURL.indexOf(context) + context.length());
        return url;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        String url = determineTargetUrl(request, response);
        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception e) {
            response.sendError(
                    HttpServletResponse.SC_FORBIDDEN,
                    "Access is denied");
        }
    }

}
