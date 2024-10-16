package com.daejangjangi.backend.basic;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.global.exception.ForbiddenException;
import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.global.utils.Base64Util;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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

  @PostMapping("/base64/encode")
  public ApiGlobalResponse<String> base64Encoding(@RequestBody String data) {
    return ApiGlobalResponse.ok(Base64Util.encode(data));
  }

  @PostMapping("/base64/decode")
  public ApiGlobalResponse<String> base64Decoding(@RequestBody String data) {
    return ApiGlobalResponse.ok(Base64Util.decode(data));
  }
}
