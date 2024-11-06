package com.daejangjangi.backend.qna.domain.dto;

import com.daejangjangi.backend.qna.domain.enums.QnaCategory;
import com.daejangjangi.backend.qna.domain.enums.QnaStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class QnaResponseDto {

  public record InfoList(
      @Schema(description = "회원 QnA 목록")
      List<MyInfo> myInfoList
  ) {

  }

  public record MyInfo(
      @Schema(description = "회원 QnA 아이디", type = "Long", example = "1")
      Long id,

      @Schema(description = "회원 QnA 카테고리", allowableValues = {
          "기능제안", "문의사항"
      })
      QnaCategory category,

      @Schema(description = "회원 QnA 상태", allowableValues = {
          "답변대기", "답변완료"
      })
      QnaStatus status,

      @Schema(description = "회원 QnA 질문", example = "이 사이트 뭐하는 곳이에요?")
      String question,

      @Schema(description = "회원 QnA 답변", example = "대장항문질환 환자들에게 바른 정보를 전달하고, 자가 건강관리를 실현할 수 있는 시스템입니다.")
      String answer
  ) {

  }
}
