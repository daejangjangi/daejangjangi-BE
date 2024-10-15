package com.daejangjangi.backend.post.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.post.exception.type.PostErrorType;
import lombok.Getter;

@Getter
public class NotManagedBoardException extends ClientDataException {

  private final String code;

  public NotManagedBoardException() {
    this(PostErrorType.NOT_MANAGED_BOARD.getMessage());
  }

  public NotManagedBoardException(String message) {
    super(message);
    this.code = PostErrorType.NOT_MANAGED_BOARD.name();
  }
}
