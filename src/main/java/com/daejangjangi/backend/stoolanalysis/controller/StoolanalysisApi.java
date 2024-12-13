package com.daejangjangi.backend.stoolanalysis.controller;

import com.daejangjangi.backend.global.annotation.swagger.Response200WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.Response401WithSwagger;
import com.daejangjangi.backend.global.annotation.swagger.ResponseCommonWithSwagger;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisRequestDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticInfos;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolDiagnosticResult;
import com.daejangjangi.backend.stoolanalysis.domain.dto.StoolanalysisResponseDto.StoolImageAnalysis;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Stool Analysis (배변 분석) API", description = "배변 분석 관련 API")
@ResponseCommonWithSwagger
public interface StoolanalysisApi {

  @Operation(summary = "배변 이미지 분석", tags = {"Stool Analysis (배변 분석) API"},
      description = "확장자 PNG 이미지 파일만 업로드 가능")
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolanalysisResponseDto.StoolImageAnalysis.class))),
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
  ApiGlobalResponse<StoolImageAnalysis> analyzeImage(@RequestPart MultipartFile stoolImage);

  @Operation(summary = "배변 진단", tags = {"Stool Analysis (배변 분석) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolanalysisResponseDto.StoolDiagnosticResult.class))),
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
                  ),
                  // Additional Description
                  @ExampleObject(
                      name = "BAD_REQUEST_ADDITIONAL_DESCRIPTION_SIZE",
                      summary = "배변 상태 이외의 추가 증상 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "additionalDescription : 배변 상태 이외의 추가 증상은 최대 500자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Diet Description
                  @ExampleObject(
                      name = "BAD_REQUEST_DIET_DESCRIPTION_SIZE",
                      summary = "오늘 먹은 음식 설명 사이즈 오류",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "dietDescription : 오늘 먹은 음식 설명은 최대 500자리 이하이어야 합니다."
                            ]
                          }"""
                  ),
                  // Stool Image Url
                  @ExampleObject(
                      name = "BAD_REQUEST_STOOL_IMAGE_URL_BLANK",
                      summary = "배변 이미지 분석에 사용된 이미지 aws url 미입력",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "stoolImageUrl : 배변 이미지 분석에 사용된 이미지 aws url을 입력하세요."
                            ]
                          }"""
                  ),
                  @ExampleObject(
                      name = "NOT_FOUND_STOOL_IMAGE_URL",
                      summary = "존재하지 않는 배변 이미지 데이터",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "stoolImageUrl : 존재하지 않는 배변 이미지 데이터입니다."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<StoolDiagnosticResult> diagnose(
      @Valid @RequestPart StoolanalysisRequestDto.StoolDiagnose request,
      @RequestPart(required = false) List<MultipartFile> stoolImages);

  @Operation(summary = "배변 분석 결과 저장", tags = {"Stool Analysis (배변 분석) API"})
  @Response401WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_FOUND_STOOL_DIAGNOSIS",
                      summary = "존재하지 않는 배변 진단 데이터",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "diagnosisId : 존재하지 않는 배변 진단 데이터입니다."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> register(@PathVariable("diagnosisId") Long diagnosisId);

  @Operation(summary = "배변 분석 결과 삭제", tags = {"Stool Analysis (배변 분석) API"})
  @Response401WithSwagger
  @Response200WithSwagger
  @ApiResponses(value = {
      @ApiResponse(responseCode = "400", description = "잘못된 요청",
          content = @Content(
              mediaType = "application/json",
              array = @ArraySchema(schema = @Schema(implementation = ApiGlobalResponse.class)),
              examples = {
                  @ExampleObject(
                      name = "NOT_FOUND_STOOL_DIAGNOSIS",
                      summary = "존재하지 않는 배변 진단 데이터",
                      value = """
                          {
                            "code": "BAD_REQUEST",
                            "message": "잘못된 요청입니다.",
                            "data": [
                              "diagnosisId : 존재하지 않는 배변 진단 데이터입니다."
                            ]
                          }"""
                  )
              }
          )
      )
  })
  ApiGlobalResponse<Null> remove(@PathVariable("diagnosisId") Long diagnosisId);

  @Operation(summary = "배변 분석 결과 조회", tags = {"Stool Analysis (배변 분석) API"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "OK",
          content = @Content(schema = @Schema(implementation = StoolDiagnosticInfos.class)))
  })
  @Response401WithSwagger
  ApiGlobalResponse<StoolDiagnosticInfos> getStoolInfos(@RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "1") int page);
}
