package com.daejangjangi.backend.stoolanalysis.exception.type;

import lombok.Getter;

@Getter
public enum StoolanalysisErrorType {

  NOT_FOUND_STOOL_DIAGNOSIS("존재하지 않는 배변 진단 데이터입니다."),
  NOT_FOUND_STOOL_IMAGE("존재하지 않는 배변 이미지 데이터입니다."),
  UNAUTHORIZED_STOOL("배변 데이터에 대한 권한이 없습니다.");

  private final String message;

  StoolanalysisErrorType(final String message) {
    this.message = message;
  }

}
