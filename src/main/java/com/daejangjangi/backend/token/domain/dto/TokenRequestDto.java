package com.daejangjangi.backend.token.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class TokenRequestDto {

  public record Reissue(

      @Schema(description = "리프레쉬 토큰")

      @NotBlank
      String refreshToken
  ) {

  }
}
