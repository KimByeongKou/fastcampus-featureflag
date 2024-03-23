package com.fastcampus.featureflag.usecase.port;

import lombok.AllArgsConstructor;
import lombok.Data;

public interface GetStringValuePort {
    String getStringValue(GetStringValuePortRequest request);

    @Data
    @AllArgsConstructor
    class GetStringValuePortRequest {
        private String key;
    }
}
