package com.example.hellobatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class HelloBatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(HelloBatchApplication.class, args);
    }

}
