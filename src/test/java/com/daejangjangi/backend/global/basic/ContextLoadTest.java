package com.daejangjangi.backend.global.basic;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.daejangjangi.backend.member.controller.MemberController;
import com.daejangjangi.backend.token.controller.TokenController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ContextLoadTest extends IntegrationTest {

  @Autowired
  private MemberController memberController;
  @Autowired
  private TokenController tokenController;

  @Test
  public void contextLoads() {
    assertNotNull(memberController);
    assertNotNull(tokenController);
  }
}
