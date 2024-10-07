package com.daejangjangi.backend.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  EMAIL_DUPLICATION_ERROR("중복되는 메일 주소입니다."),
  NICKNAME_DUPLICATION_ERROR("중복되는 닉네임입니다.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
