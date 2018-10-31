package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/v1/login")
public class UserSecurityController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserSecurityService userSecurityService;


    //TODO:
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public AuthenticationToken getAuthenticationToken(@RequestBody String userCredentials) throws IOException {
        logger.info("Sending request to get authentication token");
        System.out.println(userCredentials);
        AuthenticationToken authenticationToken = userSecurityService.getAuthenticationToken(userCredentials);
        //TODO: if null send bad response
        return authenticationToken;
        /* https://www.baeldung.com/spring-security-authentication-provider*/
    }

}
