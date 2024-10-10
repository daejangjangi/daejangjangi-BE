package com.daejangjangi.backend.category.exception;

import com.daejangjangi.backend.category.exception.type.CategoryErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotManagedCategoryException extends ClientDataException {

  private final String code;

  public NotManagedCategoryException() {
    this(CategoryErrorType.NOT_MANAGED_CATEGORY.getMessage());
  }

  public NotManagedCategoryException(String message) {
    super(message);
    this.code = CategoryErrorType.NOT_MANAGED_CATEGORY.name();
  }
}
