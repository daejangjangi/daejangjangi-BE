package com.daejangjangi.backend.daejangtoon.exception;

import com.daejangjangi.backend.daejangtoon.exception.type.DaejangtoonErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotFoundToonException extends ClientDataException {

  private final String code;

  public NotFoundToonException() {
    this(DaejangtoonErrorType.NOT_FOUND_TOON.getMessage());
  }

  public NotFoundToonException(final String message) {
    super(message);
    this.code = DaejangtoonErrorType.NOT_FOUND_TOON.name();
  }
}
