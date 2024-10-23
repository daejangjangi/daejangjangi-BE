package com.daejangjangi.backend.faq.domain.dto;

import com.daejangjangi.backend.faq.domain.enums.FaqCategory;
import com.daejangjangi.backend.global.annotation.validation.ValidEnum;
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
      @ValidEnum(enumClass = FaqCategory.class, message = "지원하지 않는 카테고리 입니다.")
      String category,

      @Schema(description = "FAQ 질문", example = "이 사이트 뭐하는 곳이에요?")

      @NotBlank(message = "질문을 입력하세요.")
      @Size(max = 500, message = "질문은 최대 {max}자 이하 입니다.")
      String question
  ) {

  }

  @Schema(name = "FAQAnswerRequest", description = "FAQ 답변 등록 요청 DTO")
  public record Answer(

      @Schema(description = "FAQ 아이디", type = "Long", example = "1")

      @NotNull(message = "FAQ 아이디를 입력하세요.")
      Long id,

      @Schema(description = "FAQ 답변", example = "대장항문질환 환자들에게 바른 정보를 전달하고, 자가 건강관리를 실현할 수 있는 시스템입니다.")

      @NotBlank(message = "답변을 입력하세요.")
      @Size(max = 10000, message = "답변은 최대 {max}자 이하 입니다.")
      String answer
  ) {

  }
}
