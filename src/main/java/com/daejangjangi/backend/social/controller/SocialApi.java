package com.daejangjangi.backend.social.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.social.domain.dto.SocialRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Social (소셜) API", description = "소셜 관련 API")
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
public interface SocialApi {

  @Operation(summary = "소셜 로그인", tags = {"Social (소셜) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "BAD_REQUEST_EMAIL",
                      summary = "소셜 계정 이메일 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "email : 소셜 계정 이메일을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_SNS_ID",
                      summary = "소셜 계정 고유값 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "snsId : 소셜 계정 고유값을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_PROVIDER",
                      summary = "소셜 계정 제공자 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "provider : 소셜 계정 제공자를 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "INVALID_PROVIDER_ERROR",
                      summary = "지원하지 않는 제공자",
                      value = """
                          {
                            "code": "INVALID_PROVIDER_ERROR",
                            "message": "지원하지 않는 소셜 계정 제공자입니다.",
                            "data": null
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_FOUND_MEMBER",
                      summary = "*가입하지 않은 회원",
                      description = "*해당 예외 발생 시, 회원가입 필요!",
                      value = """
                          {
                            "code": "NOT_FOUND_MEMBER",
                            "message": "존재하지 않는 회원입니다.",
                            "data": null
                          }"""
                  ),
              }
          )
      )
  })
  ApiGlobalResponse<?> socialLogin(@RequestBody SocialRequestDto.SocialLogin request);
}
