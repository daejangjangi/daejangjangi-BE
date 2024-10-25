package com.daejangjangi.backend.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public class MemberResponseDto {

  @Schema(name = "LoginResponse", description = "로그인 응답 DTO")
  public record Login(
      @Schema(description = "액세스 토큰", example = "xxxx.xxxx.xxxx")
      String accessToken,
      @Schema(description = "리프레쉬 토큰", example = "xxxx.xxxx.xxxx")
      String refreshToken
  ) {

  }

  @Schema(name = "MemberInfoResponse", description = "회원정보 응답 DTO")
  public record Info(
      @Schema(description = "닉네임", example = "nick")

      String nickname,

      @Schema(description = "생년월일", example = "2024-10-17")

      LocalDate birth,

      @Schema(description = "성별", allowableValues = {"m", "w"})

      String gender,

      @Schema(description = "회원 장질환", allowableValues = {
          "변비", "과민성장증후군_설사형", "과민성장증후군_변비형", "치질", "치핵", "치열", "변실금", "항문소양증",
          "대장암", "크론병", "궤양성대장염", "복부팽만", "없음"
      })

      List<String> diseases,

      @Schema(description = "회원 관심 상품 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "비건", "기타 장건강 간식"
      })

      List<String> categories
  ) {

  }
}
