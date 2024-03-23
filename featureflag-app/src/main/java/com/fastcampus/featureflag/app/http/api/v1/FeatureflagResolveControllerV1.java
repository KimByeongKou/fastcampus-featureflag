package com.fastcampus.featureflag.app.http.api.v1;

import com.fastcampus.featureflag.usecase.service.FeatureflagService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resolve")
public class FeatureflagResolveControllerV1 {
    private final FeatureflagService featureflagService;
    @GetMapping("/string/{key}")
    public String getStringFlagValue(
            @PathVariable String key
    ) {
        String targetKey = key.isEmpty() ? "myStringFlag" : key;
        try {
            return featureflagService.getStringFlag(targetKey);
        } catch (Exception e) {
            return "";
        }
    }
}
