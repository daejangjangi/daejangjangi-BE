package com.daejangjangi.backend.token.service;

import com.daejangjangi.backend.global.exception.UnAuthenticatedException;
import com.daejangjangi.backend.token.exception.ExpiredTokenException;
import com.daejangjangi.backend.token.exception.InvalidJwtSignatureException;
import com.daejangjangi.backend.token.exception.InvalidTokenException;
import com.daejangjangi.backend.token.exception.NotAuthenticatedAccessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenValidator {

  private final SecretKey ACCESS_SECRET;
  private final SecretKey REFRESH_SECRET;

  public TokenValidator(
      @Value("${jwt.access.secret}") String accessSecret,
      @Value("${jwt.refresh.secret}") String refreshSecret
  ) {
    byte[] accessKeyBytes = Decoders.BASE64.decode(accessSecret);
    byte[] refreshKeyBytes = Decoders.BASE64.decode(refreshSecret);
    this.ACCESS_SECRET = Keys.hmacShaKeyFor(accessKeyBytes);
    this.REFRESH_SECRET = Keys.hmacShaKeyFor(refreshKeyBytes);
  }

  /**
   * accessToken 검증
   *
   * @param accessToken
   * @return Claims
   */
  public Claims validateAccessToken(String accessToken) {
    return getClaimsFromToken(accessToken, ACCESS_SECRET);
  }

  /**
   * refreshToken 검증
   *
   * @param refreshToken
   * @return Claims
   */
  public Claims validateRefreshToken(String refreshToken) {
    return getClaimsFromToken(refreshToken, REFRESH_SECRET);
  }

  /*--------------Private----------------------------Private----------------------------Private---*/

  /**
   * 토큰 parsing
   *
   * @param token
   * @param secretKey
   * @return Claims
   */
  private Claims getClaimsFromToken(String token, SecretKey secretKey) {
    try {
      if (!StringUtils.hasText(token)) {
        throw new NotAuthenticatedAccessException();
      }
      return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    } catch (ExpiredJwtException e) {
      throw new ExpiredTokenException();
    } catch (SignatureException e) {
      throw new InvalidJwtSignatureException();
    } catch (JwtException e) {
      throw new InvalidTokenException(e.getMessage());
    } catch (Exception e) {
      throw new UnAuthenticatedException(e.getMessage());
    }
  }
}

