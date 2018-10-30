package com.dkorniichuk.movieland.controller;

import com.dkorniichuk.movieland.entity.Token;
import com.dkorniichuk.movieland.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/v1/login")
public class UserController {


    //TODO:
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Token getToken(@RequestBody String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(json, User.class);
        System.out.println(user);
        return null;
       /* https://www.baeldung.com/spring-security-authentication-provider*/
    }

}
