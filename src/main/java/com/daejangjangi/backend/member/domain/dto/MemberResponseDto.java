package com.daejangjangi.backend.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public class MemberResponseDto {

  public record Info(
      @Schema(description = "닉네임")

      String nickname,

      @Schema(description = "생년월일")

      LocalDate birth,

      @Schema(description = "성별")

      String gender,

      @Schema(description = "회원 장질환")

      List<String> diseases,

      @Schema(description = "회원 관심 상품 카테고리")

      List<String> categories
  ) {

  }
}
