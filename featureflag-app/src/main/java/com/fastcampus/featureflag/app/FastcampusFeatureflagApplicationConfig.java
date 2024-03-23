package com.fastcampus.featureflag.app;

import com.fastcampus.featureflag.adapter.FeatureflagAdapterConfig;
import com.fastcampus.featureflag.usecase.FeatureflagUsecaseConfig;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Import({FeatureflagAdapterConfig.class, FeatureflagUsecaseConfig.class})
@ComponentScan(
    basePackages = {"com.fastcampus.featureflag.adapter"}
)
@ServletComponentScan
public class FastcampusFeatureflagApplicationConfig {
}