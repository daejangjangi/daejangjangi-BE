package com.daejangjangi.backend.comment.exception;

import com.daejangjangi.backend.comment.exception.type.CommentErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;

public class NotFoundComment extends ClientDataException {

  private final String code;

  public NotFoundComment() {
    this(CommentErrorType.NOT_FOUND_COMMENT.getMessage());
  }

  public NotFoundComment(final String message) {
    super(message);
    this.code = CommentErrorType.NOT_FOUND_COMMENT.name();
  }
}
