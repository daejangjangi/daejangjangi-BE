package com.daejangjangi.backend.qna.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.qna.exception.type.QnaErrorType;
import lombok.Getter;

@Getter
public class NotFoundQnaException extends ClientDataException {

  private final String code;

  public NotFoundQnaException() {
    this(QnaErrorType.NOT_FOUND_QNA.getMessage());
  }

  public NotFoundQnaException(String message) {
    super(message);
    this.code = QnaErrorType.NOT_FOUND_QNA.name();
  }
}
