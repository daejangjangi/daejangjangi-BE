package com.daejangjangi.backend.token.exception;

import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.token.exception.type.TokenErrorType;
import lombok.Getter;

@Getter
public class NotAuthenticatedAccessException extends UnAuthenticatedException {

  private final String code;

  public NotAuthenticatedAccessException() {
    this(TokenErrorType.NOT_AUTHENTICATED_ACCESS.getMessage());
  }

  public NotAuthenticatedAccessException(final String message) {
    super(message);
    this.code = TokenErrorType.NOT_AUTHENTICATED_ACCESS.name();
  }
}
