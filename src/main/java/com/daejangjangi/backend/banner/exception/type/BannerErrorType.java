package com.daejangjangi.backend.banner.exception.type;

import lombok.Getter;

@Getter
public enum BannerErrorType {

  ALREADY_EXISTS_BANNER("이미 상품의 배너가 존재합니다."),
  ;
  private final String message;

  BannerErrorType(final String message) {
    this.message = message;
  }
}
