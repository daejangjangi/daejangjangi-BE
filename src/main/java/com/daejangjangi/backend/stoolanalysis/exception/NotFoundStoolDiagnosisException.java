package com.daejangjangi.backend.stoolanalysis.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.stoolanalysis.exception.type.StoolanalysisErrorType;


public class NotFoundStoolDiagnosisException extends ClientDataException {

  private final String code;

  public NotFoundStoolDiagnosisException() {
    this(StoolanalysisErrorType.NOT_FOUND_STOOL_DIAGNOSIS.getMessage());
  }

  public NotFoundStoolDiagnosisException(final String message) {
    super(message);
    this.code = StoolanalysisErrorType.NOT_FOUND_STOOL_DIAGNOSIS.name();
  }
}
