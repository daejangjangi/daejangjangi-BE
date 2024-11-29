package com.daejangjangi.backend.banner.controller;

import com.daejangjangi.backend.banner.domain.dto.BannerResponseDto.BannerInfoList;
import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response403WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.ObjectUtils.Null;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Banner (배너) API", description = "배너 관련 API")
@ResponseCommonWithSwagger
public interface BannerApi {

  @Operation(summary = "배너 등록", tags = {"Banner (배너) API"},
      responses = {
          @ApiResponse(
              responseCode = "400",
              description = "잘못된 요청",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ApiGlobalResponse.class),
                  examples = {
                      @ExampleObject(
                          name = "NOT_FOUND_PRODUCT",
                          summary = "미등록 상품",
                          value = """
                              {
                                "code" : "NOT_FOUND_PRODUCT",
                                "message" : "존재하지 않는 상품입니다.",
                                "data" : null
                              }
                              """
                      ),
                      @ExampleObject(
                          name = "ALREADY_EXISTS_BANNER",
                          summary = "배너 중복 등록",
                          value = """
                              {
                                "code" : "ALREADY_EXISTS_BANNER",
                                "message" : "이미 상품의 배너가 존재합니다.",
                                "data" : null
                              }
                              """
                      ),
                      @ExampleObject(
                          name = "NOT_IMAGE_ERROR",
                          summary = "비이미지 파일",
                          value = """
                              {
                                "code" : "NOT_IMAGE_ERROR",
                                "message" : "이미지 파일만 업로드할 수 있습니다.",
                                "data" : null
                              }
                              """
                      ),
                      @ExampleObject(
                          name = "NOT_ACCESSIBLE_CONTENT_TYPE",
                          summary = "접근 불가 파일",
                          value = """
                              {
                                "code" : "NOT_ACCESSIBLE_CONTENT_TYPE",
                                "message" : "접근할 수 없는 파일 유형입니다.",
                                "data" : null
                              }
                              """
                      ),
                  }
              )
          )
      }
  )
  @Response200WithSwagger
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<Null> register(
      @Parameter MultipartFile bannerImage,
      @Parameter Long productId
  );

  @Operation(summary = "배너 목록 조회", tags = {"Banner (배너) API"},
      responses = {
          @ApiResponse(
              responseCode = "200",
              description = "OK",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BannerInfoList.class)
              )
          )
      })
  @Response401WithSwagger
  @Response403WithSwagger
  ApiGlobalResponse<BannerInfoList> list();

}