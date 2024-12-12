package com.daejangjangi.backend.stoolanalysis.domain.dto;

import com.daejangjangi.backend.stoolanalysis.domain.enums.Mucus;
import com.daejangjangi.backend.stoolanalysis.domain.enums.ProteinLumps;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class StoolanalysisResponseDto {

  @Schema(name = "StoolImageAiAnalysisResponse", description = "배변 이미지 AI 분석 응답 DTO")
  public record StoolImageAiAnalysis(

      @Schema(description = "배변 색상")
      Color color,

      @Schema(description = "배변 묽기")
      Form form,

      @Schema(description = "혈변 여부")
      boolean isBloody,

      @Schema(description = "배변 점액 존재 여부")
      Mucus mucus,

      @Schema(description = "배변에 뭉친 흰색 알갱이 존재 여부")
      ProteinLumps proteinLumps
  ) {

  }

  @Schema(name = "StoolImageAnalysisResponse", description = "배변 이미지 분석 응답 DTO")
  public record StoolImageAnalysis(

      @Schema(description = "배변 이미지 AI 분석 결과")
      StoolImageAiAnalysis stoolImageAiAnalysis,

      @Schema(description = "배변 이미지 url")
      String stoolImageUrl
  ) {

  }


  @Schema(name = "StoolDiagnosticAiResultResponse", description = "배변 분석 AI 진단 응답 DTO")
  public record StoolDiagnosticAiResult(
      @Schema(description = "배변 진단 결과",
          example = "보호자님께,\\n\\n아기의 변에 대한 정보를 주셔서 감사합니다.  "
              + "아이보리색의 딱딱한 변에 피가 섞여 있고, 점액이나 단백질 덩어리는 없으며, "
              + "모유만 먹는다는 설명을 바탕으로 몇 가지 가능성을 고려해 볼 필요가 있습니다... ")
      @JsonProperty("user_language")
      String userLanguage

  ) {

  }

  @Schema(name = "StoolDiagnosticResultResponse", description = "배변 진단 응답 DTO")
  public record StoolDiagnosticResult(
      @Schema(description = "배변 진단 결과",
          example = "보호자님께,\\n\\n아기의 변에 대한 정보를 주셔서 감사합니다.  "
              + "아이보리색의 딱딱한 변에 피가 섞여 있고, 점액이나 단백질 덩어리는 없으며, "
              + "모유만 먹는다는 설명을 바탕으로 몇 가지 가능성을 고려해 볼 필요가 있습니다... ")
      String diagnosticResult,

      @Schema(description = "배변 분석일")
      LocalDateTime date,
      @Schema(description = "배변 묽기")
      Form form,
      @Schema(description = "배변 색상")
      Color color
  ) {

  }

}
