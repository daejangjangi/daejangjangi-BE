package com.daejangjangi.backend.global.exception;

import com.daejangjangi.backend.global.exception.type.ApiGlobalErrorType;
import lombok.Getter;

@Getter
public class UnAuthenticatedException extends RuntimeException {

  private final String code;

  public UnAuthenticatedException() {
    this(ApiGlobalErrorType.UNAUTHENTICATED.getMessage());
  }

  public UnAuthenticatedException(String message) {
    super(message);
    this.code = ApiGlobalErrorType.UNAUTHENTICATED.name();
  }
}
