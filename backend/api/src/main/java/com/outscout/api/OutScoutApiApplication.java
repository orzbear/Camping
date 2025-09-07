package com.outscout.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class OutScoutApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutScoutApiApplication.class, args);
    }
}
