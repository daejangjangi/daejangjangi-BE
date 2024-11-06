package com.daejangjangi.backend.qna.domain.dto;

import com.daejangjangi.backend.global.annotation.validation.ValidEnum;
import com.daejangjangi.backend.qna.domain.enums.QnaCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class QnaRequestDto {

  @Schema(name = "QnaRegisterRequest", description = "QnA 등록 요청 DTO")
  public record Register(

      @Schema(description = "QnA 카테고리", allowableValues = {
          "기능제안", "문의사항"
      })
      @NotBlank(message = "카테고리를 입력하세요.")
      @ValidEnum(enumClass = QnaCategory.class, message = "지원하지 않는 카테고리 입니다.")
      String category,

      @Schema(description = "QnA 질문", example = "이 사이트 뭐하는 곳이에요?")
      @NotBlank(message = "질문을 입력하세요.")
      @Size(max = 500, message = "질문은 최대 {max}자 이하 입니다.")
      String question
  ) {

  }

  @Schema(name = "QnaAnswerRequest", description = "QnA 답변 등록 요청 DTO")
  public record Answer(
      @Schema(description = "QnA 아이디", type = "Long", example = "1")
      @NotNull(message = "QnA 아이디를 입력하세요")
      Long id,

      @Schema(description = "QnA 답변", example = "대장항문질환 환자들에게 바른 정보를 전달하고, 자가 건강관리를 실현할 수 있는 시스템입니다.")

      @NotBlank(message = "답변을 입력하세요.")
      @Size(max = 500, message = "답변은 최대 {max}자 이하 입니다.")
      String answer
  ) {

  }
}
