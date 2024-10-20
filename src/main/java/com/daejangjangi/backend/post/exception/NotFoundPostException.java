package com.daejangjangi.backend.post.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.post.exception.type.PostErrorType;
import lombok.Getter;

@Getter
public class NotFoundPostException extends ClientDataException {

  private final String code;

  public NotFoundPostException() {
    this(PostErrorType.NOT_FOUND_POST.getMessage());
  }

  public NotFoundPostException(String message) {
    super(message);
    this.code = PostErrorType.NOT_FOUND_POST.name();
  }
}
