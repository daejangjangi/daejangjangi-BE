package com.daejangjangi.backend.reels.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.reels.domain.dto.ReelsRequestDto;
import com.daejangjangi.backend.reels.domain.dto.ReelsResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Reels (릴스) API", description = "릴스 관련 API")
@ResponseCommonWithSwagger
public interface ReelsApi {

  @Operation(summary = "릴스 등록", tags = {"Reels (릴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "BAD_REQUEST",
                          summary = "잘못된 요청",
                          value = """
                              "code": "BAD_REQUEST",
                              "message": "잘못된 요청입니다.",
                              "data": [
                                 "title : 릴스 제목을 입력하세요.",
                                 "title : 릴스 제목은 최대 50이하이어야 합니다."
                              ]
                              """
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
                      )
                  }
              )
          )
      }
  )
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> register(
      @Parameter ReelsRequestDto.Register request,
      @Parameter MultipartFile reelsVideo
  );

  @Operation(summary = "릴스 제거", tags = {"Reels (릴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_REELS",
                          summary = "미등록 릴스",
                          value = """
                              {
                                "code": "NOT_FOUND_REELS",
                                "message": "존재하지 않는 릴스입니다.",
                                "data": null
                              }
                              """
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
      @Parameter Long reelsId
  );

  @Operation(summary = "릴스 목록 조회", tags = {"Reels (릴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ReelsResponseDto.ReelsInfoList.class)
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
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<ReelsResponseDto.ReelsInfoList> list();

  @Operation(summary = "릴스 상세 조회", tags = {"Reels (릴스) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ReelsResponseDto.ReelsInfo.class)
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
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<ReelsResponseDto.ReelsInfo> info(
      @Parameter Long reelsId
  );
}
