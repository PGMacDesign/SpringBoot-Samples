package com.pgmacdesign.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created on 2018-04-23 by PGMacDesign
 */
@SpringBootApplication
@EnableScheduling
public class MainLauncher {

    public static void main(String[] args){
        SpringApplication.run(MainLauncher.class, args);
    }
}
