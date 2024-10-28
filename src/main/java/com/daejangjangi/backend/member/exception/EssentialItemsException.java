package com.daejangjangi.backend.member.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import lombok.Getter;

@Getter
public class EssentialItemsException extends ClientDataException {

  private final String code;

  public EssentialItemsException() {
    this(MemberErrorType.ESSENTIAL_ITEMS_ERROR.getMessage());
  }

  public EssentialItemsException(final String message) {
    super(message);
    this.code = MemberErrorType.ESSENTIAL_ITEMS_ERROR.name();
  }
}
