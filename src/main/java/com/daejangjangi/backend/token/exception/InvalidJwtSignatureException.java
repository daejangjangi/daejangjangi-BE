package com.daejangjangi.backend.token.exception;

import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.token.exception.type.TokenErrorType;
import lombok.Getter;

@Getter
public class InvalidJwtSignatureException extends UnAuthenticatedException {

  private final String code;

  public InvalidJwtSignatureException() {
    this(TokenErrorType.INVALID_JWT_SIGNATURE.getMessage());
  }

  public InvalidJwtSignatureException(final String message) {
    super(message);
    code = TokenErrorType.INVALID_JWT_SIGNATURE.name();
  }
}
