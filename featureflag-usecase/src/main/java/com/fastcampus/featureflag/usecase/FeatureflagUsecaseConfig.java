package com.fastcampus.featureflag.usecase;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
    basePackages =  {
        "com.fastcampus.featureflag.usecase",
    }
)
public class FeatureflagUsecaseConfig {
}
