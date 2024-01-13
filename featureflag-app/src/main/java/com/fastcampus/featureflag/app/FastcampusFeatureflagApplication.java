package com.fastcampus.featureflag.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @ComponentScan(basePackages = {"com.fastcampus.featureflag"})
public class FastcampusFeatureflagApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastcampusFeatureflagApplication.class, args);

    }
}