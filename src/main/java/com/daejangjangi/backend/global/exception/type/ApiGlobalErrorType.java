package com.daejangjangi.backend.global.exception.type;

import lombok.Getter;

@Getter
public enum ApiGlobalErrorType {

  NO_STATIC_RESOURCE("제공하지 않는 URL 입니다."),
  BAD_REQUEST("잘못된 요청입니다."),
  UNAUTHENTICATED("인증에 실패하였습니다."),
  FORBIDDEN("리소스에 권한이 없습니다."),
  INTERNAL_SERVER_ERROR("서버 내부 오류 입니다.");

  private final String message;

  ApiGlobalErrorType(String message) {
    this.message = message;
  }
}
