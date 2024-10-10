package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.token.domain.dto.TokenDto;
import com.daejangjangi.backend.token.domain.dto.TokenRequestDto;
import com.daejangjangi.backend.token.domain.entity.Token;
import com.daejangjangi.backend.token.exception.InvalidTokenException;
import com.daejangjangi.backend.token.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class TokenService {

  private final AuthProvider authProvider;
  private final TokenProvider tokenProvider;
  private final TokenValidator tokenValidator;
  private final TokenRepository tokenRepository;

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

  /**
   * 액세스 토큰 재발급
   *
   * @param request
   * @return TokenDto
   */
  @Transactional
  public TokenDto reissueToken(TokenRequestDto.Reissue request) {
    String refreshToken = request.refreshToken();
    if (!StringUtils.hasText(refreshToken)) {
      throw new InvalidTokenException();
    }
    Claims claims = tokenValidator.validateRefreshToken(refreshToken);
    Authentication authentication = authProvider.getAuthentication(claims);
    String reissuedAccessToken = tokenProvider.generateAccessToken(authentication);
    String reissuedRefreshToken = tokenProvider.generateRefreshToken(authentication);
    update(refreshToken, reissuedRefreshToken);
    return TokenDto.builder()
        .accessToken(reissuedAccessToken)
        .refreshToken(reissuedRefreshToken)
        .build();
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 토큰 저장
   *
   * @param memberId
   * @param refreshToken
   */
  private void save(String memberId, String refreshToken) {
    Token token = Token.builder()
        .memberId(memberId)
        .refreshToken(refreshToken)
        .build();
    tokenRepository.save(token);
  }

  /**
   * 토큰 업데이트
   *
   * @param oldRefreshToken
   * @param newRefreshToken
   */
  private void update(String oldRefreshToken, String newRefreshToken) {
    Token token = tokenRepository.findByRefreshToken(oldRefreshToken)
        .orElseThrow(InvalidTokenException::new);
    token.updateRefreshToken(newRefreshToken);
  }
}