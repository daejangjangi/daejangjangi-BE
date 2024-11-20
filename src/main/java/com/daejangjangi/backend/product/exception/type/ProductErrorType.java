package com.daejangjangi.backend.product.exception.type;

import lombok.Getter;

@Getter
public enum ProductErrorType {
  NOT_FOUND_PRODUCT("존재하지 않는 상품입니다."),
  ;

  private final String message;

  ProductErrorType(final String message) {
    this.message = message;
  }
}
