package com.fastcampus.featureflag.app.http.api.v1;

import com.fastcampus.featureflag.usecase.service.FeatureflagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(
        operationId = "getStringFlagValue",
        summary = "String type 의 flag 를 조회합니다.",
        description = "String type 의 flag 를 조회합니다. \n" +
            "flag 가 존재하지 않을 경우 빈 문자열을 반환합니다.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ok", content = @io.swagger.v3.oas.annotations.media.Content(schema = @Schema(implementation = String.class))),
        }
    )
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
