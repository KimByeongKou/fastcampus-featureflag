package com.fastcampus.featureflag.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fastcampus.featureflag"})
public class FastcampusFeatureflagApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastcampusFeatureflagApplication.class, args);

    }
}