package com.fastcampus.featureflag.app;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(
    basePackages = {"com.fastcampus.featureflag.adapter"}
)
@ServletComponentScan
public class FastcampusFeatureflagApplicationConfig {
}