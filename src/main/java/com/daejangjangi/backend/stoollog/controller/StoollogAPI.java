package com.daejangjangi.backend.stoollog.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.stoollog.domain.dto.StoollogRequestDto;
import com.daejangjangi.backend.stoollog.domain.dto.StoollogResponseDto.StoollogInfoListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import java.time.LocalDate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Stool log (배변 일지) API", description = "배변 일지 관련 API")
@ResponseCommonWithSwagger
public interface StoollogAPI {

  @Operation(summary = "배변 일지 등록", tags = {"Stool log (배변 일지) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Color
                  @ExampleObject(
                      name = "BAD_REQUEST_COLOR_BLANK",
                      summary = "배변 색상 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "color : 배변 색상을 선택해주세요."
                            ]
                          }"""
                  ),
                  // Form
                  @ExampleObject(
                      name = "BAD_REQUEST_FORM_BLANK",
                      summary = "배변 형태 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "form : 배변 형태를 선택해주세요."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> save(
      @Valid @RequestBody StoollogRequestDto.StoollogRegisterRequest request);

  @Operation(summary = "배변 일지 수정", tags = {"Stool log (배변 일지) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  // Color
                  @ExampleObject(
                      name = "BAD_REQUEST_COLOR_BLANK",
                      summary = "배변 색상 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "color : 배변 색상을 선택해주세요."
                            ]
                          }"""
                  ),
                  // Form
                  @ExampleObject(
                      name = "BAD_REQUEST_FORM_BLANK",
                      summary = "배변 형태 미선택",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "form : 배변 형태를 선택해주세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_AUTHORIZED_STOOLLOG",
                      summary = "배변 일지 수정 권한 없음",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "배변 일지 수정 및 삭제 권한이 없습니다.",
                            "data": null
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> update(
      @Valid @RequestBody StoollogRequestDto.StoollogModifyRequest request);

  @Operation(summary = "배변 일지 삭제", tags = {"Stool log (배변 일지) API"})
  @Response200WithSwagger
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_AUTHORIZED_STOOLLOG",
                      summary = "배변 일지 삭제 권한 없음",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "배변 일지 수정 및 삭제 권한이 없습니다.",
                            "data": null
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> delete(@PathVariable("stoollogId") Long stoollogId);

  @Operation(summary = "일별로 배변 일지 조회", tags = {"Stool log (배변 일지) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = StoollogInfoListResponse.class)
              )
          )
      }
  )
  @Response401WithSwagger
  ApiGlobalResponse<StoollogInfoListResponse> getStoollogs(
      @Schema(description = "날짜", example = "2024-11-26")
      @RequestParam LocalDate date);

}
