package com.daejangjangi.backend.global.config.token;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.member.domain.dto.MemberRequestDto;
import com.daejangjangi.backend.member.exception.type.MemberErrorType;
import com.daejangjangi.backend.token.domain.dto.TokenDto;
import com.daejangjangi.backend.token.service.TokenService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final TokenService tokenService;

  public TokenAuthenticationFilter(
      AuthenticationManager manager,
      TokenService tokenService
  ) {
    super(manager);
    this.tokenService = tokenService;
  }

  private final Gson gson = new GsonBuilder().serializeNulls().create();

  private static final String CONTENT_TYPE = "application/json";
  private static final String CHARACTER_ENCODING = "UTF-8";

  /**
   * 1. 인증 전처리 - 로그인 요청 정보 기반 Authentication 반환
   *
   * @param request  from which to extract parameters and perform the authentication
   * @param response the response, which may be needed if the implementation has to do a redirect as
   *                 part of a multi-stage authentication process (such as OIDC).
   * @return Authentication
   * @throws AuthenticationException
   */
  @Override
  public Authentication attemptAuthentication(HttpServletRequest request,
      HttpServletResponse response) throws AuthenticationException {
    MemberRequestDto.Login loginRequest;
    // todo : 추후 예외 처리 필요. - 어떤 예외 처리를 해야할 지 고민
    try (Reader reader = new InputStreamReader(request.getInputStream())) {
      loginRequest = gson.fromJson(reader, MemberRequestDto.Login.class);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return getAuthenticationManager().authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.email(), loginRequest.password(), new ArrayList<>()
        )
    );
  }

  /**
   * 3-1. 인증 후처리 - 로그인 성공 시 처리 로직
   *
   * @param request
   * @param response
   * @param chain
   * @param authResult the object returned from the <tt>attemptAuthentication</tt> method.
   * @throws IOException
   * @throws ServletException
   */
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain, Authentication authResult) {
    TokenDto tokenDto = tokenService.getToken(authResult);
    writeResponse(response, HttpStatus.OK.value(), ApiGlobalResponse.ok(tokenDto));
  }

  /**
   * 3-2. 인증 후처리 - 로그인 실패 시 처리 로직
   *
   * @param request
   * @param response
   * @param failed
   * @throws IOException
   */
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request,
      HttpServletResponse response, AuthenticationException failed) {
    log.error("Unsuccessful authentication", failed);
    writeResponse(response, HttpStatus.UNAUTHORIZED.value(),
        ApiGlobalResponse.error(
            MemberErrorType.NOT_MATCH_CREDENTIAL.name(),
            MemberErrorType.NOT_MATCH_CREDENTIAL.getMessage()
        ));
  }

  private <T> void writeResponse(HttpServletResponse response, int status,
      ApiGlobalResponse<T> data) {
    response.setContentType(CONTENT_TYPE);
    response.setCharacterEncoding(CHARACTER_ENCODING);
    response.setStatus(status);
    try (Writer writer = response.getWriter()) {
      gson.toJson(data, writer);
      writer.flush();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

