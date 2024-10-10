package com.daejangjangi.backend.basic;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.daejangjangi.backend.global.config.SecurityConfig;
import com.daejangjangi.backend.global.exception.type.ApiGlobalErrorType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("NoAsciiCharacters")
@TestPropertySource(properties = "logging.config=classpath:logback-spring-test.xml")
@WebMvcTest(BasicController.class)
@Import(SecurityConfig.class)
public class BasicControllerTest {

  @Autowired
  protected MockMvc mvc;

  @Test
  @DisplayName("서비스 정상 연결 확인")
  void health_check_test() throws Exception {
    mvc.perform(get("/health-check"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(HttpStatus.OK.name()))
        .andExpect(jsonPath("$.message").value(HttpStatus.OK.getReasonPhrase()));
  }

  @Test
  @DisplayName("예외 처리 확인 - BadRequest")
  void exception_handling_test_400() throws Exception {
    mvc.perform(get("/exception/bad_request"))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(ApiGlobalErrorType.BAD_REQUEST.name()))
        .andExpect(jsonPath("$.message").value(ApiGlobalErrorType.BAD_REQUEST.getMessage()));
  }

  @Test
  @DisplayName("예외 처리 확인 - UnAuthenticated")
  void exception_handling_test_401() throws Exception {
    mvc.perform(get("/exception/unauthenticated"))
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(ApiGlobalErrorType.UNAUTHENTICATED.name()))
        .andExpect(jsonPath("$.message").value(ApiGlobalErrorType.UNAUTHENTICATED.getMessage()));
  }

  @Test
  @DisplayName("예외 처리 확인 - Forbidden")
  void exception_handling_test_403() throws Exception {
    mvc.perform(get("/exception/forbidden"))
        .andExpect(status().isForbidden())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(ApiGlobalErrorType.FORBIDDEN.name()))
        .andExpect(jsonPath("$.message").value(ApiGlobalErrorType.FORBIDDEN.getMessage()));
  }

  @Test
  @DisplayName("예외 처리 확인 - InternalServerError")
  void exception_handling_test_500() throws Exception {
    mvc.perform(get("/exception/internal_server_error"))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(ApiGlobalErrorType.INTERNAL_SERVER_ERROR.name()))
        .andExpect(
            jsonPath("$.message").value(ApiGlobalErrorType.INTERNAL_SERVER_ERROR.getMessage()));
  }

  @Test
  @DisplayName("예외 처리 확인 - 없는 리소스 접근")
  void exception_handling_test_no_resource() throws Exception {
    String noResourceUrl = "no-resource";
    mvc.perform(get("/" + noResourceUrl))
        .andExpect(status().isBadRequest())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.code").value(ApiGlobalErrorType.NO_STATIC_RESOURCE.name()))
        .andExpect(jsonPath("$.message").value(ApiGlobalErrorType.NO_STATIC_RESOURCE.getMessage()));
  }
}
