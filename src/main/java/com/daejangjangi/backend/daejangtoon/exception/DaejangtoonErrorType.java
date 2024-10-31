package com.daejangjangi.backend.daejangtoon.exception;

import lombok.Getter;

@Getter
public enum DaejangtoonErrorType {

  NOT_FOUND_TOON("존재하지 않는 툰입니다."),
  NOT_FOUND_CHAPTER("존재하지 않는 회차입니다."),
  ;

  private final String message;

  DaejangtoonErrorType(String message) {
    this.message = message;
  }
}
