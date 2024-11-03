package com.daejangjangi.backend.cardnews.exception;

import com.daejangjangi.backend.cardnews.exception.type.CardnewsErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotFoundCardnewsException extends ClientDataException {

  private final String code;

  public NotFoundCardnewsException() {
    this(CardnewsErrorType.NOT_FOUND_CARDNEWS.getMessage());
  }

  public NotFoundCardnewsException(final String message) {
    super(message);
    this.code = CardnewsErrorType.NOT_FOUND_CARDNEWS.name();
  }
}
