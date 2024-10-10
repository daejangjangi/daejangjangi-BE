package com.daejangjangi.backend.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openAPI() {
    Info info = new Info();
    info.title("Daejangjangi Api Documentation").description("대장장이 API 문서입니다.")
        .version("1.0.0");

    SecurityScheme jwtSecurityScheme = new SecurityScheme().type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");

    SecurityRequirement schemaRequirement = new SecurityRequirement()
        .addList("jwt");

    return new OpenAPI()
        .components(new Components().addSecuritySchemes("jwt", jwtSecurityScheme))
        .addSecurityItem(schemaRequirement)
        .info(info);
  }
}

