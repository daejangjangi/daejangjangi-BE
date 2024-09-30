package com.daejangjangi.backend.basic;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.global.exception.ForbiddenException;
import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

  @GetMapping("/health-check")
  public ApiGlobalResponse<String> healthCheck() {
    return ApiGlobalResponse.ok();
  }

  @GetMapping("/exception/{name}")
  public void exceptionHandlingCheck(@PathVariable String name) {
    switch (name.toUpperCase()) {
      case "BAD_REQUEST" -> throw new ClientDataException();
      case "UNAUTHENTICATED" -> throw new UnAuthenticatedException();
      case "FORBIDDEN" -> throw new ForbiddenException();
      case "INTERNAL_SERVER_ERROR" -> throw new RuntimeException("INTERNAL_SERVER_ERROR");
      default -> throw new RuntimeException("default");
    }
  }
}
