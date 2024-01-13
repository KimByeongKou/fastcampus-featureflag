package com.fastcampus.featureflag.usecase.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public interface GetStringValuePort {
    String getStringValue(GetStringValuePortRequest request) throws Exception;
    String getStringValueWithDefaultValue(GetStringValuePortRequest request, String defaultValue);


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class GetStringValuePortRequest {
        private String key;
    }
}
