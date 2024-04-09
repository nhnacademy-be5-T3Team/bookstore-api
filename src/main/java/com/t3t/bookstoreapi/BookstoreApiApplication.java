package com.t3t.bookstoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@ConfigurationPropertiesScan
@SpringBootApplication
public class BookstoreApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookstoreApiApplication.class, args);
    }
}
