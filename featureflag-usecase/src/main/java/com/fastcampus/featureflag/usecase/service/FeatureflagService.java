package com.fastcampus.featureflag.usecase.service;

import com.fastcampus.featureflag.usecase.port.GetStringValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FeatureflagService {
    private final GetStringValuePort getStringValuePort;
    public String getStringFlag(String key)  {
        return getStringValuePort.getStringValue(new GetStringValuePort.GetStringValuePortRequest(key));
    }
}
