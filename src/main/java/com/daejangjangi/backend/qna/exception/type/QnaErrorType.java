package com.daejangjangi.backend.qna.exception.type;

import lombok.Getter;

@Getter
public enum QnaErrorType {

  NOT_FOUND_QNA("존재하지 않는 질문입니다.");
  private final String message;

  QnaErrorType(final String message) {
    this.message = message;
  }
}
