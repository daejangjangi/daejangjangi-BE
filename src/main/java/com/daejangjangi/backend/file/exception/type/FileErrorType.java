package com.daejangjangi.backend.file.exception.type;

import lombok.Getter;

@Getter
public enum FileErrorType {

  EMPTY_FILE_ERROR("파일이 비어 있습니다."),
  NOT_IMAGE_ERROR("이미지 파일만 업로드할 수 있습니다."),
  NOT_SUPPORTED_FORMAT("잘못된 형식의 파일입니다.");

  private final String message;

  FileErrorType(String message) {
    this.message = message;
  }
}
