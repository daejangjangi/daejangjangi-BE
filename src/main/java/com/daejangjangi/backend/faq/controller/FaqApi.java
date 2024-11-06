package com.daejangjangi.backend.faq.controller;

import com.daejangjangi.backend.faq.domain.dto.FaqRequestDto;
import com.daejangjangi.backend.faq.domain.dto.FaqResponseDto;
import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
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
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "FAQ (자주 묻는 질문) API", description = "자주 묻는 질문 관련 API")
@ResponseCommonWithSwagger
public interface FaqApi {

  @Operation(summary = "자주 묻는 질문 등록", tags = {"FAQ (자주 묻는 질문) API"},
      responses = {
          @ApiResponse(responseCode = "400", description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "BAD_REQUEST",
                          summary = "잘못된 입력",
                          value = """
                              {
                                "code": "BAD_REQUEST",
                                "message": "잘못된 요청입니다.",
                                "data": [
                                  "id : QnA 아이디를 입력하세요."
                                ]
                              }"""
                      ),
                      @ExampleObject(
                          name = "QNA_DUPLICATION_ERROR",
                          summary = "QnA 중복 등록",
                          value = """
                                "code": "QNA_DUPLICATION_ERROR",
                                "message": "이미 등록된 QnA 입니다.",
                                "data": null
                              """
                      )
                  }
              )
          )
      }
  )
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> register(@RequestBody FaqRequestDto.Register request);

  @Operation(summary = "자주 묻는 질문 목록 조회", tags = {"FAQ (자주 묻는 질문) API"},
      responses = {
          @ApiResponse(responseCode = "200", description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(
                      schema = @Schema(implementation = FaqResponseDto.FaqList.class))))
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<FaqResponseDto.FaqList> faqs();

  @Operation(
      summary = "자주 묻는 질문 삭제",
      tags = {"FAQ (자주 묻는 질문) API"},
      responses = {
          @ApiResponse(responseCode = "400", description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_FAQ",
                          summary = "미등록 FAQ",
                          value = """
                                "code": "NOT_FOUND_FAQ",
                                "message": "존재하지 않는 FAQ 입니다.",
                                "data": null
                              """
                      )
                  }
              )
          )
      }
  )
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> remove(
      @Parameter Long faqId
  );
}
