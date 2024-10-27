package com.daejangjangi.backend.file.exception;

import com.daejangjangi.backend.file.exception.type.FileErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotImageFileException extends ClientDataException {

  private final String code;

  public NotImageFileException() {
    this(FileErrorType.NOT_IMAGE_ERROR.getMessage());
  }

  public NotImageFileException(String message) {
    super(message);
    this.code = FileErrorType.NOT_IMAGE_ERROR.name();
  }
}
