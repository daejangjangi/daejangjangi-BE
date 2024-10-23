package com.daejangjangi.backend.faq.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FaqResponseDto {

  @Schema(name = "FaqsResponse", description = "FAQ 응답 DTO")
  public record Faqs(

      @Schema(description = "FAQ 카테고리", allowableValues = {
          "USAGE_ERROR", "REQUEST"
      })
      String category,

      @Schema(description = "FAQ 질문", example = "이 사이트 뭐하는 곳이에요?")
      String question,

      @Schema(description = "FAQ 답변", example = "대장항문질환 환자들에게 바른 정보를 전달하고, 자가 건강관리를 실현할 수 있는 시스템입니다.")
      String answer
  ) {

  }
}
