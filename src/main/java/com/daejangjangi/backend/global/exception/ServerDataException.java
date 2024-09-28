package com.daejangjangi.backend.global.exception;

import lombok.Getter;

@Getter
public class ServerDataException extends RuntimeException {

  private final String code;

  public ServerDataException() {
    this(ApiGlobalErrorType.INTERNAL_SERVER_ERROR.getMessage());
  }

  public ServerDataException(String message) {
    super(message);
    this.code = ApiGlobalErrorType.INTERNAL_SERVER_ERROR.name();
  }
}
