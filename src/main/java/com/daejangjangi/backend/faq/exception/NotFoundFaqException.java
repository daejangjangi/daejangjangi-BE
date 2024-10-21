package com.daejangjangi.backend.faq.exception;

import com.daejangjangi.backend.faq.exception.type.FaqErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotFoundFaqException extends ClientDataException {

  private final String code;

  public NotFoundFaqException() {
    this(FaqErrorType.NOT_FOUND_FAQ.getMessage());
  }

  public NotFoundFaqException(String message) {
    super(message);
    this.code = FaqErrorType.NOT_FOUND_FAQ.name();
  }
}
