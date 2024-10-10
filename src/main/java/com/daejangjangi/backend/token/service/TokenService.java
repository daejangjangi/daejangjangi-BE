package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.token.domain.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final TokenProvider tokenProvider;

  public TokenDto getToken(Authentication authentication) {
    String accessToken = tokenProvider.generateAccessToken(authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication);
    return TokenDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  /*--------------Private----------------------------Private----------------------------Private---*/
  private void saveOrUpdate(String refreshToken) {

  }
}