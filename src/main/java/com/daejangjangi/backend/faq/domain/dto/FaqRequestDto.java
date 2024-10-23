package com.daejangjangi.backend.faq.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class FaqRequestDto {

  @Schema(name = "FAQRegisterRequest", description = "FAQ 등록 요청 DTO")
  public record Register(

      @Schema(description = "FAQ 카테고리", allowableValues = {
          "USAGE_ERROR", "REQUEST"
      })

      @NotBlank(message = "카테고리를 입력하세요.")
      String category,

      @Schema(description = "FAQ 질문")

      @NotBlank(message = "질문을 입력하세요.")
      @Size(max = 500, message = "질문은 최대 {max}자 이하 입니다.")
      String question
  ) {

  }

  @Schema(name = "FAQAnswerRequest", description = "FAQ 답변 등록 요청 DTO")
  public record Answer(

      @Schema(description = "FAQ 아이디", type = "Long")

      @NotNull(message = "FAQ 아이디를 입력하세요.")
      Long id,

      @Schema(description = "FAQ 답변")

      @NotBlank(message = "답변을 입력하세요.")
      @Size(max = 10000, message = "답변은 최대 {max}자 이하 입니다.")
      String answer
  ) {

  }
}
