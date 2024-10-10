package com.daejangjangi.backend.member.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import lombok.Getter;

@Getter
public class NotFoundMemberException extends ClientDataException {

  private final String code;

  public NotFoundMemberException() {
    this(MemberErrorType.NOT_FOUND_MEMBER.getMessage());
  }

  public NotFoundMemberException(String message) {
    super(message);
    this.code = MemberErrorType.NOT_FOUND_MEMBER.name();
  }
}
