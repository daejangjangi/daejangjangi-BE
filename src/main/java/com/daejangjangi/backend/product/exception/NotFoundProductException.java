package com.daejangjangi.backend.product.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.product.exception.type.ProductErrorType;
import lombok.Getter;

@Getter
public class NotFoundProductException extends ClientDataException {

  private final String code;

  public NotFoundProductException() {
    this(ProductErrorType.NOT_FOUND_PRODUCT.getMessage());
  }

  public NotFoundProductException(final String message) {
    super(message);
    this.code = ProductErrorType.NOT_FOUND_PRODUCT.name();
  }
}
