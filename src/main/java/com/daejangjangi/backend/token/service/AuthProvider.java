package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.member.domain.entity.Member;
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

  /**
   * Member 정보 기반 Authentication 발급
   *
   * @param member
   * @return
   */
  public Authentication getAuthentication(Member member) {
    List<SimpleGrantedAuthority> authorities = getAuthorities(member);
    User principal = new User(String.valueOf(member.getId()), "", authorities);
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

  /**
   * Member 에서 권한 추출
   *
   * @param member
   * @return
   */
  private List<SimpleGrantedAuthority> getAuthorities(Member member) {
    return Collections.singletonList(
        new SimpleGrantedAuthority(member.getRole().name()));
  }
}
