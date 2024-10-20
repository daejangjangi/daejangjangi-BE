package com.daejangjangi.backend.faq.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FaqRequestDto {

  @Schema(name = "FAQ-Register", description = "FAQ 등록 DTO")
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
}
