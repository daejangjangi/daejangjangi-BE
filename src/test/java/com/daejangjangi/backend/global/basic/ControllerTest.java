package com.daejangjangi.backend.global.basic;

import com.daejangjangi.backend.global.config.SecurityConfig;
import com.daejangjangi.backend.global.config.token.TokenAuthInterceptor;
import com.daejangjangi.backend.global.exception.GlobalExceptionHandler;
import com.daejangjangi.backend.member.service.MemberService;
import com.daejangjangi.backend.token.service.TokenService;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ActiveProfiles("test")
@SuppressWarnings("NonAsciiCharacters")
@TestPropertySource(properties = "logging.config=classpath:logback-spring-test.xml")
@Import(SecurityConfig.class)
abstract public class ControllerTest {

  @Autowired
  protected MockMvc mvc;

  protected final Gson gson = new Gson();

  @MockBean
  protected TokenService tokenService;

  @MockBean
  protected MemberService memberService;

  @SpyBean
  protected BCryptPasswordEncoder passwordEncoder;

  @Autowired
  protected TokenAuthInterceptor tokenAuthInterceptor;

  @BeforeEach
  void setUp() {
    mvc = MockMvcBuilders.standaloneSetup(initController())
        .setControllerAdvice(GlobalExceptionHandler.class)
        .addInterceptors(tokenAuthInterceptor)
        .build();
  }

  abstract protected Object initController();

}