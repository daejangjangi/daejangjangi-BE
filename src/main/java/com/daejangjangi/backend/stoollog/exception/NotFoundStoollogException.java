package com.daejangjangi.backend.stoollog.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.stoollog.exception.type.StoollogErrorType;
import lombok.Getter;

@Getter
public class NotFoundStoollogException extends ClientDataException {

  private final String code;

  public NotFoundStoollogException() {
    this(StoollogErrorType.NOT_FOUND_STOOLLOG.getMessage());
  }

  public NotFoundStoollogException(String message) {
    super(message);
    this.code = StoollogErrorType.NOT_FOUND_STOOLLOG.name();
  }

}