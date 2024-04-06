package com.fastcampus.featureflag.adapter;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(
        basePackages =  {
                "com.fastcampus.featureflag.adapter",
        }
)
@Configuration
@RequiredArgsConstructor
public class FeatureflagAdapterConfig {
}
