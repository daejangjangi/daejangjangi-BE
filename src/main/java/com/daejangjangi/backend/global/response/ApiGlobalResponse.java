package com.daejangjangi.backend.global.response;

import com.daejangjangi.backend.global.exception.type.ApiGlobalErrorType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiGlobalResponse<T> {

  private final String code;

  private final String message;

  private final T data;

  private ApiGlobalResponse(final String code, final String message, final T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public static <T> ApiGlobalResponse<T> ok() {
    return ok(null);
  }

  public static <T> ApiGlobalResponse<T> ok(final T data) {
    return new ApiGlobalResponse<T>(HttpStatus.OK.name(), HttpStatus.OK.getReasonPhrase(), data);
  }

  public static <T> ApiGlobalResponse<T> error(ApiGlobalErrorType errorType) {
    return error(errorType.name(), errorType.getMessage());
  }

  public static <T> ApiGlobalResponse<T> error(ApiGlobalErrorType errorType, T data) {
    return error(errorType.name(), errorType.getMessage(), data);
  }

  public static <T> ApiGlobalResponse<T> error(String code, String message) {
    return error(code, message, null);
  }

  public static <T> ApiGlobalResponse<T> error(String code, String message, T data) {
    return new ApiGlobalResponse<T>(code, message, data);
  }
}
