package com.fastcampus.featureflag.adapter.openfeatureflag;

import com.fastcampus.featureflag.usecase.port.GetStringValuePort;
import dev.openfeature.sdk.Client;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FlagdAdapter implements GetStringValuePort {

    // featureflag(flagd) SDK client instance
    private final Client flagdClient;
    @Override
    public String getStringValue(GetStringValuePortRequest request) {
        String result= flagdClient.getStringValue(request.getKey(), "null");
        log.info("Flagd returned: {}", result);
        if( result.equals("null")){
            log.info("Flagd returned null for key: {}", request.getKey());
            throw new RuntimeException("Flagd returned null for key: " + request.getKey());
        }
        return result;
    }
}
