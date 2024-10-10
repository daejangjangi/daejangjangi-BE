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

  public Authentication getAuthentication(Claims claims) {
    List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
    User principal = new User(claims.getSubject(), "", authorities);
    return new UsernamePasswordAuthenticationToken(principal, null, authorities);
  }

  private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
    return Collections.singletonList(
        new SimpleGrantedAuthority(claims.get(TokenProvider.ROLE_CLAIM).toString()));
  }
}
