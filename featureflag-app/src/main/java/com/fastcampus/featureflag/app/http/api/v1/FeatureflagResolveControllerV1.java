package com.fastcampus.featureflag.app.http.api.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resolve")
public class FeatureflagResolveControllerV1 {
    @GetMapping("/string")
    public String getStringFlagValue() {
        return "helloWorld";
    }
}
