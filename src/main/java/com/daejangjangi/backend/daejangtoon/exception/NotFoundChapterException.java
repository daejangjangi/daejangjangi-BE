package com.daejangjangi.backend.daejangtoon.exception;

import com.daejangjangi.backend.daejangtoon.exception.type.DaejangtoonErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotFoundChapterException extends ClientDataException {

  private final String code;

  public NotFoundChapterException() {
    this(DaejangtoonErrorType.NOT_FOUND_CHAPTER.getMessage());
  }

  public NotFoundChapterException(final String message) {
    super(message);
    this.code = DaejangtoonErrorType.NOT_FOUND_CHAPTER.name();
  }
}
