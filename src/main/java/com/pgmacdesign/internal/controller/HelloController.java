package com.pgmacdesign.internal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * To map to the /hello path
 */
@RestController  //Controller also exists
public class HelloController {


    @RequestMapping("/hello") //This maps to all http methods (post, get, etc)
    public String sayHi(){
        return "Hi there!";
    }
}
