package com.daejangjangi.backend.newsletter.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterRequestDto;
import com.daejangjangi.backend.newsletter.domain.dto.NewsletterResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Newsletter (뉴스레터) API", description = "뉴스레터 관련 API")
@ResponseCommonWithSwagger
public interface NewsletterApi {

  @Operation(summary = "뉴스레터 등록", tags = {"Newsletter (뉴스레터) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          value = """
                                      {
                                "code" : "BAD_REQUEST",
                                "message" : "잘못된 요청입니다.",
                                "data" : [
                                  "title : 제목을 입력해주세요.",
                                  "subTitle : 소제목을 입력해주세요.",
                                  "description : 설명을 입력하세요.(줄개행 문자 포함)",
                                  "category : 뉴스레터 카테고리를 선택해주세요."
                                ]
                              }
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
  ApiGlobalResponse<Null> register(
      @Parameter NewsletterRequestDto.Register request,
      @Parameter MultipartFile profileImage
  );

  @Operation(summary = "뉴스레터 삭제", tags = {"Newsletter (뉴스레터) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_NEWSLETTER",
                          value = """
                              {
                              "code" : "NOT_FOUND_NEWSLETTER",
                              "message" : "존재하지 않는 뉴스레터입니다.",
                              "data" : null
                              }
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
      @Parameter Long newsletterId
  );

  @Operation(summary = "뉴스레터 목록 조회", tags = {"Newsletter (뉴스레터) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NewsletterResponseDto.NewsletterInfoList.class)
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<NewsletterResponseDto.NewsletterInfoList> list();

  @Operation(summary = "뉴스레터 상세 조회", tags = {"Newsletter (뉴스레터) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = NewsletterResponseDto.NewsletterInfo.class)
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
                          name = "NOT_FOUND_NEWSLETTER",
                          value = """
                              {
                              "code" : "NOT_FOUND_NEWSLETTER",
                              "message" : "존재하지 않는 뉴스레터입니다.",
                              "data" : null
                              }
                              """
                      )
                  }
              )
          )
      }
  )
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<NewsletterResponseDto.NewsletterInfo> info(
      @Parameter Long newsletterId
  );
}
