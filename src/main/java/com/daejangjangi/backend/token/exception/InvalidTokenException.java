package com.daejangjangi.backend.token.exception;

import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.token.exception.type.TokenErrorType;
import lombok.Getter;

@Getter
public class InvalidTokenException extends UnAuthenticatedException {

  private final String code;

  public InvalidTokenException() {
    this(TokenErrorType.INVALID_TOKEN_ERROR.getMessage());
  }

  public InvalidTokenException(final String message) {
    super(message);
    this.code = TokenErrorType.INVALID_TOKEN_ERROR.name();
  }
}
