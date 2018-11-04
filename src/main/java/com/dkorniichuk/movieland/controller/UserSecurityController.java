package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.entity.AuthenticationToken;
import com.dkorniichuk.movieland.entity.User;
import com.dkorniichuk.movieland.service.UserSecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@Controller
public class UserSecurityController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserSecurityService userSecurityService;


    @RequestMapping(value = "/v1/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<AuthenticationToken> getAuthenticationToken(@RequestBody String userCredentials) throws IOException {
        logger.info("Sending request to get authentication token");
        System.out.println(userCredentials);
        AuthenticationToken authenticationToken = userSecurityService.getAuthenticationToken(userCredentials);
        return authenticationToken != null
                ? new ResponseEntity<>(authenticationToken, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @RequestMapping(value = "/v1/logout", method = RequestMethod.DELETE)
    public ResponseEntity<HttpStatus> logout(@RequestHeader String uuid) {
        logger.info("Sending request to log out");
        userSecurityService.removeAuthenticationToken(UUID.fromString(uuid));
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
