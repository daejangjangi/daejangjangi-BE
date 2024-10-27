package com.daejangjangi.backend.file.exception;

import com.daejangjangi.backend.file.exception.type.FileErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class EmptyFileException extends ClientDataException {

  private final String code;

  public EmptyFileException() {
    this(FileErrorType.EMPTY_FILE_ERROR.getMessage());
  }

  public EmptyFileException(String message) {
    super(message);
    this.code = FileErrorType.EMPTY_FILE_ERROR.name();
  }
}
