package com.daejangjangi.backend.file.exception;

import com.daejangjangi.backend.file.exception.type.FileErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotAccessibleContentTypeException extends ClientDataException {

  private final String code;

  public NotAccessibleContentTypeException() {
    this(FileErrorType.NOT_ACCESSIBLE_CONTENT_TYPE.getMessage());
  }

  public NotAccessibleContentTypeException(final String message) {
    super(message);
    this.code = FileErrorType.NOT_ACCESSIBLE_CONTENT_TYPE.name();
  }
}
