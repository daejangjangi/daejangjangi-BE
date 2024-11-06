package com.daejangjangi.backend.faq.exception;

import com.daejangjangi.backend.faq.exception.type.FaqErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class QnaDuplicationException extends ClientDataException {

  private final String code;

  public QnaDuplicationException() {
    this(FaqErrorType.QNA_DUPLICATION_ERROR.getMessage());
  }

  public QnaDuplicationException(final String message) {
    super(message);
    this.code = FaqErrorType.QNA_DUPLICATION_ERROR.name();
  }
}
