package com.sinans.ecommercebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class ECommercebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommercebackendApplication.class, args);
    }

}
