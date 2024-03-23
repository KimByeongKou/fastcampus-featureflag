package com.fastcampus.featureflag.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;


@Import(FastcampusFeatureflagApplicationConfig.class)
@SpringBootApplication
public class FastcampusFeatureflagApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastcampusFeatureflagApplication.class, args);

    }
}