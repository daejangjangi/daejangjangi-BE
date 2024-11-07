package com.daejangjangi.backend.faq.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public class FaqRequestDto {

  @Schema(name = "FAQRegisterRequest", description = "FAQ 등록 요청 DTO")
  public record Register(
      @Schema(description = "FAQ에 등록할 QnA 아이디", type = "Long", example = "1")

      @NotNull(message = "QnA 아이디를 입력하세요.")
      Long id
  ) {

  }
}
