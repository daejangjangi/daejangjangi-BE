package com.daejangjangi.backend.cardnews.exception.type;

import lombok.Getter;

@Getter
public enum CardnewsErrorType {

  NOT_FOUND_CARDNEWS("존재하지 않는 카드뉴스입니다.");

  private final String message;

  CardnewsErrorType(final String message) {
    this.message = message;
  }
}
