package com.daejangjangi.backend.stoolanalysis.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.stoolanalysis.exception.type.StoolanalysisErrorType;

public class NotFoundStoolImageException extends ClientDataException {

  private final String code;

  public NotFoundStoolImageException() {
    this(StoolanalysisErrorType.NOT_FOUND_STOOL_IMAGE.getMessage());
  }

  public NotFoundStoolImageException(final String message) {
    super(message);
    this.code = StoolanalysisErrorType.NOT_FOUND_STOOL_IMAGE.name();
  }
}
