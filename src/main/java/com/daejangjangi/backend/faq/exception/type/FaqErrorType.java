package com.daejangjangi.backend.faq.exception.type;

import lombok.Getter;

@Getter
public enum FaqErrorType {
  INVALID_FAQ_CATEGORY_ERROR("지원하지 않는 FAQ 카테고리 입니다.");

  private final String message;

  FaqErrorType(String message) {
    this.message = message;
  }
}
