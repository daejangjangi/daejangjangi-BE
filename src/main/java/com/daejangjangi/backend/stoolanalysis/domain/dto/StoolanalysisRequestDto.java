package com.daejangjangi.backend.stoolanalysis.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;

public class StoolanalysisRequestDto {


  @Schema(name = "StoolImageAnalyzeRequest", description = "배변 이미지 분석 요청 DTO")
  public record ImageAnalyze(
      String imageUrl
  ) {

  }

}
