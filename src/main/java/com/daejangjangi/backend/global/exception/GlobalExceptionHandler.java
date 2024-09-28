package com.daejangjangi.backend.global.exception;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  /**
   * Bean Validation 예외 처리
   */
  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiGlobalResponse<?> handler(BindException e) {
    List<String> errorMessageList = e.getFieldErrors().stream().map(
        b -> b.getField() + " : " + b.getDefaultMessage()
    ).toList();
    return ApiGlobalResponse.error(ApiGlobalErrorType.BAD_REQUEST, errorMessageList);
  }

  /**
   * 잘못된 요청인 경우 예외 처리
   */
  @ExceptionHandler(ClientDataException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiGlobalResponse<?> handler(ClientDataException e) {
    return ApiGlobalResponse.error(e.getCode(), e.getMessage());
  }

  /**
   * 인증 실패 시 예외 처리
   */
  @ExceptionHandler(UnAuthenticatedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ApiGlobalResponse<?> handler(UnAuthenticatedException e) {
    return ApiGlobalResponse.error(e.getCode(), e.getMessage());
  }

  /**
   * 권한이 없는 리소스 접근 시 예외 처리
   */
  @ExceptionHandler(ForbiddenException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiGlobalResponse<?> handler(ForbiddenException e) {
    return ApiGlobalResponse.error(e.getCode(), e.getMessage());
  }

  /**
   * 이외 예상치 못한 RuntimeException 예외 처리
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ApiGlobalResponse<?> handler(Exception e) {
    log.error("Exception : ", e);
    return ApiGlobalResponse.error(ApiGlobalErrorType.INTERNAL_SERVER_ERROR);
  }
}
