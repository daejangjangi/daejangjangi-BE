package com.daejangjangi.backend.comment.exception.type;

import lombok.Getter;

@Getter
public enum CommentErrorType {
  NOT_FOUND_COMMENT("존재하지 않는 댓글입니다."),
  NOT_COMMENT_AUTHOR("댓글의 작성자가 아니므로 수정할 수 없습니다.");
  private final String message;

  CommentErrorType(String message) {
    this.message = message;
  }

}
