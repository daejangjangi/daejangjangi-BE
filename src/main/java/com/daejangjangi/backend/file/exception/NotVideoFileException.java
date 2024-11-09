package com.daejangjangi.backend.file.exception;

import com.daejangjangi.backend.file.exception.type.FileErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotVideoFileException extends ClientDataException {

  private final String code;

  public NotVideoFileException() {
    this(FileErrorType.NOT_VIDEO_ERROR.getMessage());
  }

  public NotVideoFileException(final String message) {
    super(message);
    this.code = FileErrorType.NOT_VIDEO_ERROR.name();
  }
}
