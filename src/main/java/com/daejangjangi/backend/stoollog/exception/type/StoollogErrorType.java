package com.daejangjangi.backend.stoollog.exception.type;

import lombok.Getter;

@Getter
public enum StoollogErrorType {
  NOT_FOUND_STOOLLOG("존재하지 않는 배변 일지입니다."),
  NOT_AUTHORIZED_STOOLLOG("배변 일지 수정 및 삭제 권한이 없습니다.");

  private final String message;

  private StoollogErrorType(final String message) {
    this.message = message;
  }
}
