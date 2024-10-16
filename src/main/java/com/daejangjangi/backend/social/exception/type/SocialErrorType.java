package com.daejangjangi.backend.social.exception.type;

import lombok.Getter;

@Getter
public enum SocialErrorType {

  INVALID_PROVIDER_ERROR("지원하지 않는 소셜 계정 제공자입니다.");

  private final String message;

  SocialErrorType(String message) {
    this.message = message;
  }
}
