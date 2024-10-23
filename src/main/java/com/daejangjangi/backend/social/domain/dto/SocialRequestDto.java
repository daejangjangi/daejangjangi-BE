package com.daejangjangi.backend.social.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SocialRequestDto {

  @Schema(name = "SocialLoginRequest", description = "소셜 로그인 요청 DTO")
  public record SocialLogin(
      @Schema(description = "소셜 계정 이메일", example = "daejangjangi@email.com")

      @Email(message = "이메일 형식이 맞지 않습니다.")
      @NotBlank(message = "소셜 계정 이메일을 입력하세요.")
      String email,

      @Schema(description = "소셜 계정 고유값", example = "avDa3d@1fsc...")

      @NotBlank(message = "소셜 계정 고유값을 입력하세요.")
      String snsId,

      @Schema(description = "소셜 계정 제공자", enumAsRef = true)

      @NotBlank(message = "소셜 계정 제공자를 입력하세요.")
      String provider
  ) {

  }
}
