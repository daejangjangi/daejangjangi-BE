package com.daejangjangi.backend.token.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenProvider {

  private final SecretKey ACCESS_SECRET;
  private final SecretKey REFRESH_SECRET;
  private final Long ACCESS_EXP;
  private final Long REFRESH_EXP;

  public static final String ROLE_CLAIM = "role";

  public TokenProvider(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.access.exp}") Long accessExp,
      @Value("${jwt.refresh.secret}") String refreshSecret,
      @Value("${jwt.refresh.exp}") Long refreshExp
  ) {
    byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecret);
    byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecret);
    this.ACCESS_SECRET = Keys.hmacShaKeyFor(accessKeyBytes);
    this.REFRESH_SECRET = Keys.hmacShaKeyFor(refreshKeyBytes);
    this.ACCESS_EXP = accessExp;
    this.REFRESH_EXP = refreshExp;
  }

  /**
   * 액세스 토큰 발급
   *
   * @param authentication
   * @return String - accessToken
   */
  public String generateAccessToken(Authentication authentication) {
    return createToken(authentication, ACCESS_SECRET, ACCESS_EXP);
  }

  /**
   * 리프레쉬 토큰 발급
   *
   * @param authentication
   * @return String - refreshToken
   */
  public String generateRefreshToken(Authentication authentication) {
    return createToken(authentication, REFRESH_SECRET, REFRESH_EXP);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * JWT 생성 로직
   *
   * @param authentication
   * @param secret
   * @param exp
   * @return String
   */
  private String createToken(Authentication authentication, SecretKey secret, Long exp) {
    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + exp);

    String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining());

    return Jwts.builder()
        .subject(authentication.getName())
        .claim(ROLE_CLAIM, authorities)
        .issuedAt(now)
        .expiration(expiredDate)
        .signWith(secret)
        .compact();
  }
}
