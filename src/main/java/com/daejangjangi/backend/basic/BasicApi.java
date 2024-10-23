package com.daejangjangi.backend.basic;

import com.daejangjangi.backend.global.annotation.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Basic (기본) API", description = "기본 제공 API")
@ResponseCommonWithSwagger
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
