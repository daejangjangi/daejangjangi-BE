package com.daejangjangi.backend.member.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import com.daejangjangi.backend.post.exception.type.PostErrorType;
import lombok.Getter;

@Getter
public class NotAuthorException extends ClientDataException {

  private final String code;

  public NotAuthorException() {
    this(MemberErrorType.NOT_AUTHOR_ERROR.getMessage());
  }

  public NotAuthorException(final String message) {
    super(message);
    this.code = MemberErrorType.NOT_AUTHOR_ERROR.name();
  }
}
