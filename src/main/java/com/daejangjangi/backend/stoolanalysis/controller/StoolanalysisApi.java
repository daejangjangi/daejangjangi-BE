package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.ImageInfo;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
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
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Stool Analysis (배변 분석) API", description = "배변 분석 관련 API")
@ResponseCommonWithSwagger
public interface StoolanalysisApi {

  @Operation(summary = "배변 이미지 분석", tags = {"Stool Analysis (배변 분석) API"},
      description = "확장자 PNG 이미지 파일만 업로드 가능")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolanalysisResponseDto.ImageInfo.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_ACCESSIBLE_CONTENT_TYPE",
                      summary = "PNG 확장자 외 이미지 업로드 불가",
                      value = """
                          {
                             "code": "NOT_ACCESSIBLE_CONTENT_TYPE",
                             "message": "접근할 수 없는 파일 유형입니다.",
                             "data": null
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_IMAGE_ERROR",
                      summary = "이미지 파일만 업로드 가능",
                      value = """
                          {
                             "code": "NOT_IMAGE_ERROR",
                             "message": "이미지 파일만 업로드할 수 있습니다.",
                             "data": null
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<ImageInfo> analyzeImage(@RequestPart MultipartFile stoolImage);

  @Operation(summary = "배변 진단", tags = {"Stool Analysis (배변 분석) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolanalysisResponseDto.StoolDiagnosticResult.class)))
  })
  ApiGlobalResponse<StoolDiagnosticResult> diagnose(
      @Valid @RequestBody StoolanalysisRequestDto.StoolDiagnose request);

  @Operation(summary = "배변 분석 결과 일지에 등록", tags = {"Stool Analysis (배변 분석) API"},
      description = "확장자 PNG 이미지 파일만 업로드 가능")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolanalysisResponseDto.ImageInfo.class))),
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_ACCESSIBLE_CONTENT_TYPE",
                      summary = "PNG 확장자 외 이미지 업로드 불가",
                      value = """
                          {
                             "code": "NOT_ACCESSIBLE_CONTENT_TYPE",
                             "message": "접근할 수 없는 파일 유형입니다.",
                             "data": null
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_IMAGE_ERROR",
                      summary = "이미지 파일만 업로드 가능",
                      value = """
                          {
                             "code": "NOT_IMAGE_ERROR",
                             "message": "이미지 파일만 업로드할 수 있습니다.",
                             "data": null
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> register(@Valid @RequestPart StoolanalysisRequestDto.Register request,
      @RequestPart(required = false) List<MultipartFile> stoolImages);
}
