package com.daejangjangi.backend.member.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import lombok.Getter;

@Getter
public class NicknameDuplicationException extends ClientDataException {

  private final String code;

  public NicknameDuplicationException() {
    this(MemberErrorType.NICKNAME_DUPLICATION_ERROR.getMessage());
  }

  public NicknameDuplicationException(final String message) {
    super(message);
    this.code = MemberErrorType.NICKNAME_DUPLICATION_ERROR.name();
  }
}
