package com.daejangjangi.backend.file.exception.type;

import lombok.Getter;

@Getter
public enum FileErrorType {

  EMPTY_FILE_ERROR("파일이 비어 있습니다."),
  NOT_IMAGE_ERROR("이미지 파일만 업로드할 수 있습니다."),
  NOT_VIDEO_ERROR("비디오 파일만 업로드할 수 있습니다."),
  NOT_SUPPORTED_FORMAT("잘못된 형식의 파일입니다."),
  NOT_ACCESSIBLE_CONTENT_TYPE("접근할 수 없는 파일 유형입니다.");

  private final String message;

  FileErrorType(final String message) {
    this.message = message;
  }
}
