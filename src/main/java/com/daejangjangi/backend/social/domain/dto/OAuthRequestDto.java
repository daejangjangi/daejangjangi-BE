package com.daejangjangi.backend.social.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class OAuthRequestDto {

  public record SocialLogin(
      @Schema(description = "소셜 계정 이메일")

      @NotBlank
      String email,

      @Schema(description = "소셜 계정 고유값")

      @NotBlank
      String snsId,

      @Schema(description = "소셜 계정 제공자")

      @NotBlank
      String provider
  ) {

  }
}
