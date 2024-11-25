package com.daejangjangi.backend.product.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.product.exception.type.ProductErrorType;
import lombok.Getter;

@Getter
public class InvalidProductGroupException extends ClientDataException {

  private final String code;

  public InvalidProductGroupException() {
    this(ProductErrorType.INVALID_PRODUCT_GROUP.getMessage());
  }

  public InvalidProductGroupException(final String message) {
    super(message);
    this.code = ProductErrorType.INVALID_PRODUCT_GROUP.name();
  }
}
