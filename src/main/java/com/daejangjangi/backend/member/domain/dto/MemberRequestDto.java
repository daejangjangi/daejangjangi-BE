package com.daejangjangi.backend.member.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public class MemberRequestDto {

  @Schema(description = "회원가입 DTO")
  public record Join(
      @Schema(description = "이메일")

      @Email(message = "이메일 형식이 맞지 않습니다.")
      @NotBlank(message = "이메일을 입력하세요.")
      @Size(max = 50, message = "이메일은 최대 {max}자리 이하이어야 합니다.")
      String email,

      @Schema(description = "비밀번호")

      @NotBlank(message = "비밀번호를 입력하세요.")
      @Size(min = 8, max = 16, message = "비밀번호는 최소 {min}자리 이상, 최대 {max}자리 이하이어야 합니다.")
      @Pattern(regexp = "(?=.*[0-9]).+", message = "비밀번호에 숫자가 포함되어야 합니다.")
      @Pattern(regexp = "(?=.*[a-zA-Z]).+", message = "비밀번호에 영문자가 포함되어야 합니다.")
      @Pattern(regexp = "(?=.*[!@#$%^&*()\\[\\]{}]).+", message = "비밀번호에 특수문자(!,@,#,$,%,^,&,*,(,),[,])가 포함되어야 합니다.")
      String password,

      @Schema(description = "닉네임")

      @NotBlank(message = "닉네임을 입력하세요.")
      @Size(max = 5, message = "닉네임은 최대 {max}자 이하이어야 합니다.")
      @Pattern(regexp = "^[A-Za-z가-힣0-9]+$", message = "닉네임은 영문자,한글,숫자로만 입력해야 합니다.")
      String nickname,

      @Schema(description = "성별")

      @NotBlank(message = "성별을 입력하세요.")
      @Size(max = 1, message = "성별은 최대 {max}자 이하이어야 합니다.")
      @Pattern(regexp = "^[mw]+$", message = "성별은 m(남성) 또는 w(여성)를 입력해야 합니다.")
      String gender,

      @Schema(description = "생년월일", example = "2024-10-17")

      @NotNull(message = "생일을 입력하세요.")
      LocalDate birth,

      @Schema(description = "회원 장질환", allowableValues = {
          "변비", "과민성장증후군_설사형", "과민성장증후군_변비형", "치질", "치핵", "치열", "변실금", "항문소양증",
          "대장암", "크론병", "궤양성대장염", "복부팽만", "없음"
      })

      @NotNull(message = "질병을 선택해주세요.")
      @Size(min = 1, message = "질병을 최소 {min}개 이상 선택 바랍니다.")
      List<String> diseases,

      @Schema(description = "회원 관심 상품 카테고리", allowableValues = {
          "유산균", "식이섬유", "저포드맵", "비건", "기타 장건강 간식"
      })

      @NotNull(message = "관심 상품을 선택해주세요.")
      @Size(min = 1, message = "관심 상품을 최소 {min}개 이상 선택 바랍니다.")
      List<String> categories
  ) {

  }

  @Schema(description = "로그인 DTO")
  public record Login(

      @Schema(description = "이메일")

      @NotBlank(message = "이메일을 입력해주세요.")
      String email,

      @Schema(description = "비밀번호")

      @NotBlank(message = "비밀번호를 입력해주세요.")
      String password
  ) {

  }
}