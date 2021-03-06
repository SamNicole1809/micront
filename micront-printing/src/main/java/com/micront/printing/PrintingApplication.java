package com.micront.printing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PrintingApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrintingApplication.class, args);
    }

}
