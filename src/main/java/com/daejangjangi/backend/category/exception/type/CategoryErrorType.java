package com.daejangjangi.backend.category.exception.type;

import lombok.Getter;

@Getter
public enum CategoryErrorType {

  NOT_MANAGED_CATEGORY("관리되지 않는 카테고리입니다.");
  private final String message;

  CategoryErrorType(String message) {
    this.message = message;
  }
}
