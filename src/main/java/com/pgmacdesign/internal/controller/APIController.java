package com.pgmacdesign.internal.controller;

import com.pgmacdesign.internal.datamodels.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {


    @RequestMapping("/getUser") //This maps to all http methods (post, get, etc)
    public User getTheUser(){
        User user = new User();
        user.setFirstName("Pat");
        user.setAge(32);
        user.setLastName("Mac");
        user.setUsername("@Pmac");
        return user;
    }
}
