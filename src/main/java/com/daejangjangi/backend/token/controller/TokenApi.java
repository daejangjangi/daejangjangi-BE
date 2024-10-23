package com.daejangjangi.backend.token.controller;

import com.daejangjangi.backend.global.annotation.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.token.domain.dto.TokenRequestDto;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Token (토큰) API", description = "토큰 관련 API")
@ResponseCommonWithSwagger
public interface TokenApi {

  @Operation(summary = "토큰 재발급", tags = {"Token (토큰) API"})
  @Response401WithSwagger
  @Response403WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = ApiGlobalResponse.class),
              examples = @ExampleObject(
                  value = """
                      {
                        "code": "BAD_REQUEST",
                        "message": "잘못된 요청입니다.",
                        "data": [
                          "refreshToken : 리프레쉬 토큰을 입력하세요."
                        ]
                      }
                      """
              )
          )
      )
  })
  ApiGlobalResponse<TokenResponseDto> reissue(@RequestBody TokenRequestDto.Reissue request);
}