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

  public record Join(
      @Schema(description = "이메일")

      @Email
      @NotBlank
      @Size(max = 50)
      String email,

      @Schema(description = "비밀번호")

      @NotBlank
      @Size(min = 8, max = 16)
      @Pattern(regexp = "(?=.*[0-8])(?=.*[a-zA-Z])(?=.*[!@#$%^&*()\\[\\]{}]).+")
      String password,

      @Schema(description = "닉네임")

      @NotBlank
      @Size(max = 5)
      @Pattern(regexp = "^[A-Za-z가-힣0-9]+$")
      String nickname,

      @Schema(description = "성별")

      @NotBlank
      @Size(max = 1)
      @Pattern(regexp = "^[mw]+$")
      String gender,

      @Schema(description = "생년월일")

      @NotNull
      LocalDate birth,

      @Schema(description = "회원 장질환")

      @NotNull
      @Size(min = 1)
      List<String> diseases,

      @Schema(description = "회원 관심 상품 카테고리")

      @NotNull
      @Size(min = 1)
      List<String> categories
  ) {
    
  }

  public record Login(

      @Schema(description = "이메일")

      @NotBlank
      String email,

      @Schema(description = "비밀번호")

      @NotBlank
      String password
  ) {

  }
}
