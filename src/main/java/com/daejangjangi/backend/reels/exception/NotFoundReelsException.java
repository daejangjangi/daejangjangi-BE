package com.daejangjangi.backend.reels.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.reels.exception.type.ReelsErrorType;
import lombok.Getter;

@Getter
public class NotFoundReelsException extends ClientDataException {

  private final String code;

  public NotFoundReelsException() {
    this(ReelsErrorType.NOT_FOUND_REELS.getMessage());
  }

  public NotFoundReelsException(String message) {
    super(message);
    this.code = ReelsErrorType.NOT_FOUND_REELS.name();
  }
}
