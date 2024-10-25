package com.daejangjangi.backend.file.exception;

import com.daejangjangi.backend.file.exception.type.FileErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotSupportedFileFormatException extends ClientDataException {

  private final String code;

  public NotSupportedFileFormatException(final String fileName) {
    super(FileErrorType.NOT_SUPPORTED_FORMAT.getMessage() + " : " + fileName);
    this.code = FileErrorType.NOT_SUPPORTED_FORMAT.name();
  }
}
