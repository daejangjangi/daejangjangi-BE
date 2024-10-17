package com.daejangjangi.backend.basic;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Basic (기본) API", description = "기본 제공 API")
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "OK",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiGlobalResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                      "code": "OK",
                      "message": "OK",
                      "data": null
                    }"""
            )
        )
    ),
    @ApiResponse(responseCode = "500", description = "서버 내부 오류",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ApiGlobalResponse.class),
            examples = @ExampleObject(
                value = """
                    {
                      "code": "INTERNAL_SERVER_ERROR",
                      "message": "서버 내부 오류 입니다.",
                      "data": null
                    }"""
            )
        )
    )
})
public interface BasicApi {

  @Operation(summary = "서비스 정상 여부 확인", tags = {"Basic (기본) API"})
  ApiGlobalResponse<String> healthCheck();

  @Operation(summary = "예외 핸들링", tags = {"Basic (기본) API"})
  void exceptionHandlingCheck(@Parameter String name);

  @Operation(summary = "인코딩 - base64", tags = {"Basic (기본) API"})
  ApiGlobalResponse<String> base64Encoding(@RequestBody String data);

  @Operation(summary = "디코딩 - base64", tags = {"Basic (기본) API"})
  ApiGlobalResponse<String> base64Decoding(@RequestBody String data);
}
