package com.daejangjangi.backend.basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

  @GetMapping("/health-check")
  public String healthCheck() {
    return "This service is available";
  }
}
