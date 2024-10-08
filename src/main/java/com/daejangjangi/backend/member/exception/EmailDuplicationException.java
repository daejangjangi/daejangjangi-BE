package com.daejangjangi.backend.member.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import lombok.Getter;

@Getter
public class EmailDuplicationException extends ClientDataException {

  private final String code;

  public EmailDuplicationException() {
    this(MemberErrorType.EMAIL_DUPLICATION_ERROR.getMessage());
  }

  public EmailDuplicationException(final String message) {
    super(message);
    this.code = MemberErrorType.EMAIL_DUPLICATION_ERROR.name();
  }
}
