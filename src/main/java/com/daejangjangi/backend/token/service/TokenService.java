package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.token.domain.dto.TokenDto;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final AuthProvider authProvider;
  private final TokenProvider tokenProvider;
  private final TokenValidator tokenValidator;

  private static final String Bearer = "Bearer ";

  public TokenDto getToken(Authentication authentication) {
    String accessToken = tokenProvider.generateAccessToken(authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication);
    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  public String extractFromAuthorizationHeader(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(header) && header.startsWith(Bearer)) {
      return header.replace(Bearer, "");
    }
    return null;
  }

  public void validateToken(String accessToken) {
    tokenValidator.validateAccessToken(accessToken);
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = tokenValidator.validateAccessToken(accessToken);
    return authProvider.getAuthentication(claims);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/
  private void saveOrUpdate(String refreshToken) {

  }
}