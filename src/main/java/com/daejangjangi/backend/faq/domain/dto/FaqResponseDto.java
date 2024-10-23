package com.daejangjangi.backend.faq.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class FaqResponseDto {

  @Schema(name = "FaqsResponse", description = "FAQ 응답 DTO")
  public record Faqs(

      @Schema(description = "FAQ 카테고리")
      String category,

      @Schema(description = "FAQ 질문")
      String question,

      @Schema(description = "FAQ 답변")
      String answer
  ) {

  }
}
