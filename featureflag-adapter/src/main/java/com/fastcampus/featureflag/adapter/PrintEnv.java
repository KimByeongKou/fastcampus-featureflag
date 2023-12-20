package com.fastcampus.featureflag.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class PrintEnv {
    @Autowired
    public PrintEnv(Environment environment) {
        for (String profile : environment.getActiveProfiles()) {
            System.out.println("현재 활성화된 프로파일: " + profile);
        }
        String e = environment.getProperty("fastcampus.featureflag.adapter.env1");
        System.out.println("env1: " + e);
    }
}
