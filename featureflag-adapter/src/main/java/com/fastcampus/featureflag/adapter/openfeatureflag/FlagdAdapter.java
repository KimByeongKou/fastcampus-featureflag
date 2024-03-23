package com.fastcampus.featureflag.adapter.openfeatureflag;

import com.fastcampus.featureflag.usecase.port.GetStringValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FlagdAdapter implements GetStringValuePort {

    // featureflag(flagd) SDK client instance
    @Override
    public String getStringValue(GetStringValuePortRequest request) {
        // get feature from flagd engine
        return "defaultValue";
    }
}
