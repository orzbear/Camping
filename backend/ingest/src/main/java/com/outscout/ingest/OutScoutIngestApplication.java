package com.outscout.ingest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OutScoutIngestApplication {

    public static void main(String[] args) {
        SpringApplication.run(OutScoutIngestApplication.class, args);
    }
}
