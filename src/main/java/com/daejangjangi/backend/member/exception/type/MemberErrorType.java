package com.daejangjangi.backend.member.exception.type;

import lombok.Getter;

@Getter
public enum MemberErrorType {

  EMAIL_DUPLICATION_ERROR("중복되는 메일 주소입니다."),
  NICKNAME_DUPLICATION_ERROR("중복되는 닉네임입니다."),
  NOT_FOUND_MEMBER("존재하 않는 회원입니다."),
  NOT_MATCH_CREDENTIAL("아이디 또는 비밀번호가 잘못 되었습니다. 아이디와 비밀번호를 정확히 입력해 주세요.");

  private final String message;

  MemberErrorType(String message) {
    this.message = message;
  }
}
