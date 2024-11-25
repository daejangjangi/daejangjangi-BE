package com.daejangjangi.backend.fcm.controller;

import com.daejangjangi.backend.fcm.domain.dto.FcmRequestDto;
import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "FCM (알림) API", description = "FCM 관련 API")
@ResponseCommonWithSwagger
public interface FcmApi {

  @Operation(summary = "FCM 토큰 저장", tags = {"FCM (알림) API"})
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
                      name = "BAD_REQUEST_FCM_TOKEN",
                      summary = "FCM 토큰 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "fcmToken : FCM 토큰을 입력해주세요."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> registerFcmtoken(
      @Valid @RequestBody FcmRequestDto.FcmtokenRequest request);

  @Operation(summary = "FCM 토큰 삭제", tags = {"FCM (알림) API"})
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
                      name = "BAD_REQUEST_FCM_TOKEN",
                      summary = "FCM 토큰 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "fcmToken : FCM 토큰을 입력해주세요."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> deleteFcmtoken(
      @Valid @RequestBody FcmRequestDto.FcmtokenRequest request);
}
