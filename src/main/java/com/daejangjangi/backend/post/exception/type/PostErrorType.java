package com.daejangjangi.backend.post.exception.type;

import lombok.Getter;

@Getter
public enum PostErrorType {

  NOT_FOUND_POST("존재하지 않는 게시글입니다."),
  NOT_POST_AUTHOR("게시글의 작성자가 아니므로 수정할 수 없습니다"),
  NOT_MANAGED_BOARD("관리되지 않는 게시판 카테고리입니다.");

  private final String message;

  PostErrorType(String message) {
    this.message = message;
  }

}
