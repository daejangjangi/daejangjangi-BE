package com.daejangjangi.backend.comment.exception;

import com.daejangjangi.backend.comment.exception.type.CommentErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;

public class NotCommentAuthor extends ClientDataException {

  private final String code;

  public NotCommentAuthor() {
    this(CommentErrorType.NOT_COMMENT_AUTHOR.getMessage());
  }

  public NotCommentAuthor(final String message) {
    super(message);
    this.code = CommentErrorType.NOT_COMMENT_AUTHOR.name();
  }
}
