package com.daejangjangi.backend.faq.controller;

import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto;
import com.daejangjangi.backend.global.annotation.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.Response403WithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "FAQ (자주 묻는 질문) API", description = "자주 묻는 질문 관련 API")
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
public interface FaqApi {

  @Operation(summary = "자주 묻는 질문 등록", tags = {"FAQ (자주 묻는 질문) API"})
  @Response401WithSwagger
  @Response403WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "INVALID_FAQ_CATEGORY_ERROR",
                      summary = "관리되지 않는 FAQ 카테고리",
                      value = """
                          {
                            "code": "INVALID_FAQ_CATEGORY_ERROR",
                            "message": "지원하지 않는 FAQ 카테고리 입니다.",
                            "data": null
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_FOUND_MEMBER",
                      summary = "미가입 회원",
                      value = """
                          {
                            "code": "NOT_FOUND_MEMBER",
                            "message": "존재하지 않는 회원입니다.",
                            "data": null
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_FAQ_CATEGORY_BLANK",
                      summary = "카테고리 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "category : 카테고리를 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_FAQ_CONTENT_BLANK",
                      summary = "질문 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "question : 질문을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "BAD_REQUEST_FAQ_CONTENT_SIZE",
                      summary = "자주 묻는 질문 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "question : 문의 내용은 최대 500자 이하 입니다."
                            ]
                          }"""
                  ),
              }
          )
      )
  })
  ApiGlobalResponse<?> register(@RequestBody FaqRequestDto.Register request);
}
