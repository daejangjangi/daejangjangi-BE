package com.daejangjangi.backend.stoolanalysis.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.stoolanalysis.exception.type.StoolanalysisErrorType;

public class UnauthorizedStoolException extends ClientDataException {

  private final String code;

  public UnauthorizedStoolException() {
    this(StoolanalysisErrorType.UNAUTHORIZED_STOOL.getMessage());
  }

  public UnauthorizedStoolException(final String message) {
    super(message);
    this.code = StoolanalysisErrorType.UNAUTHORIZED_STOOL.name();
  }
}
