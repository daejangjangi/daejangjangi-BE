package com.daejangjangi.backend.token.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.token.domain.dto.TokenRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Token (토큰) API", description = "토큰 관련 API")
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
public interface TokenApi {

  @Operation(summary = "토큰 재발급", tags = {"Token (토큰) API"})
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
      ),
      @ApiResponse(responseCode = "401", description = "미인증",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHENTICATED_ACCESS",
                      summary = "인증되지 않은 접근",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "EXPIRED_TOKEN",
                      summary = "만료된 토큰",
                      value = """
                          {
                            "code": "EXPIRED_TOKEN",
                            "message": "만료된 토큰입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_JWT_SIGNATURE",
                      summary = "유효하지 않은 서명",
                      value = """
                          {
                            "code": "INVALID_JWT_SIGNATURE",
                            "message": "유효하지 않은 서명입니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "UNAUTHENTICATED",
                      summary = "인증되지 않음",
                      value = """
                          {
                            "code": "UNAUTHENTICATED",
                            "message": "로그인 후 이용 바랍니다.",
                            "data": null
                          }
                          """
                  ),
                  @ExampleObject(
                      name = "INVALID_TOKEN_ERROR",
                      summary = "유효하지 않은 토큰",
                      value = """
                          {
                            "code": "INVALID_TOKEN_ERROR",
                            "message": "유효하지 않는 토큰입니다.",
                            "data": null
                          }
                          """
                  )
              }
          )
      )
  })
  ApiGlobalResponse<?> reissue(@RequestBody TokenRequestDto.Reissue request);
}
