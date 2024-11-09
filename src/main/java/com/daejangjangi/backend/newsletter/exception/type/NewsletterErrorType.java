package com.daejangjangi.backend.newsletter.exception.type;

import lombok.Getter;

@Getter
public enum NewsletterErrorType {
  NOT_FOUND_NEWSLETTER("존재하지 않는 뉴스레터입니다.");
  private final String message;

  NewsletterErrorType(final String message) {
    this.message = message;
  }
}
