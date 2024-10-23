package com.daejangjangi.backend.post.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.post.exception.type.PostErrorType;
import lombok.Getter;

@Getter
public class NotPostAuthorException extends ClientDataException {

  private final String code;

  public NotPostAuthorException() {
    this(PostErrorType.NOT_POST_AUTHOR.getMessage());
  }

  public NotPostAuthorException(String message) {
    super(message);
    this.code = PostErrorType.NOT_POST_AUTHOR.name();
  }
}
