package com.fastcampus.featureflag.adapter.openfeature.flagd;

import com.fastcampus.featureflag.usecase.port.GetStringValuePort;
import dev.openfeature.sdk.Client;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlagdAdapter implements GetStringValuePort {
    private static final Logger logger = LoggerFactory.getLogger(FlagdAdapter.class);

    private final Client flagdClient;

    @Override
    public String getStringValue(GetStringValuePortRequest request) throws Exception {
        String result = flagdClient.getStringValue(request.getKey(), "null");
        if (result.equals("null")) {
            logger.warn("Flagd returned null for key: {}", request.getKey());
            throw new Exception("Flagd returned null for key: {}");
        }
        return result;
    }

    @Override
    public String getStringValueWithDefaultValue(GetStringValuePortRequest request, String defaultValue) {
        return flagdClient.getStringValue(request.getKey(), defaultValue);
    }
}
