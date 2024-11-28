package com.daejangjangi.backend.rank.exception.type;

import lombok.Getter;

@Getter
public enum RankErrorType {

  CLEANUP_FAIL("검색 키워드 일괄 삭제에 실패했습니다."),
  ;

  private final String message;

  RankErrorType(final String message) {
    this.message = message;
  }
}
