package com.daejangjangi.backend.token.exception;

import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.token.exception.type.TokenErrorType;
import lombok.Getter;

@Getter
public class ExpiredTokenException extends UnAuthenticatedException {

  private final String code;

  public ExpiredTokenException() {
    this(TokenErrorType.EXPIRED_TOKEN.getMessage());
  }

  public ExpiredTokenException(final String message) {
    super(message);
    this.code = TokenErrorType.EXPIRED_TOKEN.name();
  }
}
