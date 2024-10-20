package com.daejangjangi.backend.faq.exception;

import com.daejangjangi.backend.faq.exception.type.FaqErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class InvalidFaqCategoryException extends ClientDataException {

  private final String code;

  public InvalidFaqCategoryException() {
    this(FaqErrorType.INVALID_FAQ_CATEGORY_ERROR.getMessage());
  }

  public InvalidFaqCategoryException(final String message) {
    super(message);
    this.code = FaqErrorType.INVALID_FAQ_CATEGORY_ERROR.name();
  }
}
