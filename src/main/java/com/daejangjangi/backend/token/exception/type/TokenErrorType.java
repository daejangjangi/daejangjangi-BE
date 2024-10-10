package com.daejangjangi.backend.token.exception.type;

import lombok.Getter;

@Getter
public enum TokenErrorType {
  // Auth
  NOT_AUTHENTICATED_ACCESS("로그인 후 이용 바랍니다."),
  // JWT
  INVALID_TOKEN_ERROR("유효하지 않는 토큰입니다."),
  INVALID_JWT_SIGNATURE("유효하지 않은 서명입니다."),
  EXPIRED_TOKEN("만료된 토큰입니다.");

  private final String message;

  TokenErrorType(String message) {
    this.message = message;
  }
}
