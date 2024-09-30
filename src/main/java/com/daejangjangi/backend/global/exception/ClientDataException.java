package com.daejangjangi.backend.global.exception;

import lombok.Getter;

@Getter
public class ClientDataException extends RuntimeException {

  private final String code;

  public ClientDataException() {
    this(ApiGlobalErrorType.BAD_REQUEST.getMessage());
  }

  public ClientDataException(String message) {
    super(message);
    this.code = ApiGlobalErrorType.BAD_REQUEST.name();
  }
}
