package com.daejangjangi.backend.global.exception;

import com.daejangjangi.backend.global.exception.type.ApiGlobalErrorType;
import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

  private final String code;

  public ForbiddenException() {
    this(ApiGlobalErrorType.FORBIDDEN.getMessage());
  }

  public ForbiddenException(String message) {
    super(message);
    this.code = ApiGlobalErrorType.FORBIDDEN.name();
  }
}
