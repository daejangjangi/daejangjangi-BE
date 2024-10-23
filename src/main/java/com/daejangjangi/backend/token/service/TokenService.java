package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.token.domain.dto.TokenRequestDto;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
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

  /**
   * accessToken & refreshToken 발급 by Authentication
   *
   * @param authentication
   * @return TokenDto
   */
  @Transactional
  public TokenResponseDto getToken(Authentication authentication) {
    return generateToken(authentication);
  }

  /**
   * accessToken & refreshToken 발급 by Member
   *
   * @param member
   * @return
   */
  public TokenResponseDto getToken(Member member) {
    Authentication authentication = authProvider.getAuthentication(member);
    return generateToken(authentication);
  }

  /**
   * AuthorizationHeader 내에서 accessToken 추출
   *
   * @param request
   * @return String - accessToken
   */
  public String extractFromAuthorizationHeader(HttpServletRequest request) {
    String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (StringUtils.hasText(header) && header.startsWith(Bearer)) {
      return header.replace(Bearer, "");
    }
    return null;
  }

  /**
   * 액세스 토큰 검증
   *
   * @param accessToken
   */
  public void validateToken(String accessToken) {
    tokenValidator.validateAccessToken(accessToken);
  }

  /**
   * 액세스 토큰 기반 Authentication 발급
   *
   * @param accessToken
   * @return Authentication
   */
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
  public TokenResponseDto reissueToken(TokenRequestDto.Reissue request) {
    String refreshToken = request.refreshToken();
    if (!StringUtils.hasText(refreshToken)) {
      throw new InvalidTokenException();
    }
    Claims claims = tokenValidator.validateRefreshToken(refreshToken);
    Authentication authentication = authProvider.getAuthentication(claims);
    String reissuedAccessToken = tokenProvider.generateAccessToken(authentication);
    String reissuedRefreshToken = tokenProvider.generateRefreshToken(authentication);
    update(refreshToken, reissuedRefreshToken);
    return TokenResponseDto.builder()
        .accessToken(reissuedAccessToken)
        .refreshToken(reissuedRefreshToken)
        .build();
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 토큰 생성 및 저장
   *
   * @param authentication
   * @return
   */
  private TokenResponseDto generateToken(Authentication authentication) {
    String accessToken = tokenProvider.generateAccessToken(authentication);
    String refreshToken = tokenProvider.generateRefreshToken(authentication);
    Long memberId = Long.parseLong(authentication.getName());
    save(memberId, refreshToken);
    return TokenResponseDto.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  /**
   * 토큰 저장
   * <p>
   * 요구사항 : 토큰이 null 인 경우, 새로운 객체를 만들어서, memberId와 refreshToken을 초기화한 뒤 저장해주고, null이 아닌 경우,
   * refreshToken 만 업데이트하도록 구현.
   *
   * @param memberId
   * @param refreshToken
   */
  private void save(Long memberId, String refreshToken) {
    Token token = tokenRepository.findByMemberId(memberId).orElseGet(() -> Token.builder()
        .memberId(memberId)
        .build());
    token.updateRefreshToken(refreshToken);
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