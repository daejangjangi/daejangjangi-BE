package com.daejangjangi.backend.qna.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.qna.domain.dto.QnaRequestDto.Answer;
import com.daejangjangi.backend.qna.domain.dto.QnaRequestDto.Register;
import com.daejangjangi.backend.qna.domain.dto.QnaResponseDto.InfoList;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;

@Tag(name = "QnA (질문) API", description = "질문 관련 API")
@ResponseCommonWithSwagger
public interface QnaApi {

  @Operation(summary = "QnA 등록", tags = {"QnA (질문) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "INVALID_QNA_CATEGORY_ERROR",
                      summary = "관리되지 않는 QnA 카테고리",
                      value = """
                          {
                            "code": "INVALID_QNA_CATEGORY_ERROR",
                            "message": "지원하지 않는 QnA 카테고리 입니다.",
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
                      name = "BAD_REQUEST",
                      summary = "잘못된 요청",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "category : 카테고리를 입력하세요.",
                              "question : 질문을 입력하세요.",
                              "question : 문의 내용은 최대 500자 이하 입니다."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> register(
      @RequestBody Register request
  );

  @Operation(summary = "QnA 삭제", tags = {"QnA (질문) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
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
                          name = "NOT_FOUND_QNA",
                          summary = "미등록 질문",
                          value = """
                              {
                                "code": "NOT_FOUND_QNA",
                                "message": "존재하지 않는 질문입니다.",
                                "data": null
                              }"""
                      ),
                      @ExampleObject(
                          name = "NOT_AUTHOR_ERROR",
                          summary = "작성자 불일치",
                          value = """
                              {
                                "code": "NOT_AUTHOR_ERROR",
                                "message": "본 계정이 작성한 컨텐츠가 아닙니다.",
                                "data": null
                              }"""
                      ),
                  }
              )
          )
      })
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> remove(
      @Parameter Long qnaId
  );

  @Operation(summary = "회원 QnA 목록", tags = {"QnA (질문) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = InfoList.class)
              )
          ),
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
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
                  }
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<InfoList> myQnaList();

  @Operation(summary = "QnA 답변 등록", tags = {"QnA (질문) API"},
      responses = @ApiResponse(
          responseCode = "400",
          description = "잘못된 요청",
          content = @Content(
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      value = """
                          {
                          "code": "BAD_REQUEST",
                          "message": "잘못된 요청입니다.",
                          "data" : [
                            "id : QnA 아이디를 입력하세요",
                            "answer : 답변을 입력하세요.",
                            "answer : 답변은 최대 500자 이하 입니다."
                          ]
                          }
                          """
                  )
              }
          )
      ))
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> answer(
      @RequestBody Answer request
  );
}
