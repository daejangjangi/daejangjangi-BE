package com.daejangjangi.backend.token.service;

import io.jsonwebtoken.Claims;
import java.util.Collections;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

@Component
public class AuthProvider {

  /**
   * JWT Claim 기반 Authentication 발급
   *
   * @param claims
   * @return Authentication
   */
  public Authentication getAuthentication(Claims claims) {
    List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * Claim 에서 권한 추출
   *
   * @param claims
   * @return
   */
  private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
    return Collections.singletonList(
        new SimpleGrantedAuthority(claims.get(TokenProvider.ROLE_CLAIM).toString()));
  }
}
