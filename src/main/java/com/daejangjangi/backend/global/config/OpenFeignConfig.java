package com.daejangjangi.backend.global.config;

import feign.Logger;
import feign.Logger.Level;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients("com.daejangjangi.backend")
public class OpenFeignConfig {

  @Bean
  Logger.Level feignLoggerLevel() {
    return Level.BASIC;
  }

}
