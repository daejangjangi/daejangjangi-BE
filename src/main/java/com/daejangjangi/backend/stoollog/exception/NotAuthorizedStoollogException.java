package com.daejangjangi.backend.stoollog.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.stoollog.exception.type.StoollogErrorType;
import lombok.Getter;

@Getter
public class NotAuthorizedStoollogException extends ClientDataException {

  private final String code;

  public NotAuthorizedStoollogException() {
    this(StoollogErrorType.NOT_AUTHORIZED_STOOLLOG.getMessage());
  }

  public NotAuthorizedStoollogException(String message) {
    super(message);
    this.code = StoollogErrorType.NOT_AUTHORIZED_STOOLLOG.name();
  }
}
