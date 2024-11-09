package com.daejangjangi.backend.reels.exception.type;

import lombok.Getter;

@Getter
public enum ReelsErrorType {
  NOT_FOUND_REELS("존재하지 않는 릴스입니다.");

  private final String message;

  private ReelsErrorType(final String message) {
    this.message = message;
  }
}
