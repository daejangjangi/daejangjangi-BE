package com.daejangjangi.backend.faq.exception.type;

import lombok.Getter;

@Getter
public enum FaqErrorType {
  INVALID_FAQ_CATEGORY_ERROR("지원하지 않는 FAQ 카테고리 입니다."),
  NOT_FOUND_FAQ("존재하지 않는 FAQ 입니다."),
  QNA_DUPLICATION_ERROR("이미 등록된 QnA 입니다.");

  private final String message;

  FaqErrorType(final String message) {
    this.message = message;
  }
}
