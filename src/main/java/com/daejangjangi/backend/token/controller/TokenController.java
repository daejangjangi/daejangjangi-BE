package com.daejangjangi.backend.token.controller;

import com.daejangjangi.backend.global.response.ApiGlobalResponse;
import com.daejangjangi.backend.token.domain.dto.TokenRequestDto;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
import com.daejangjangi.backend.token.service.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tokens")
public class TokenController implements TokenApi {

  private final TokenService tokenService;

  @PostMapping("/reissue")
  public ApiGlobalResponse<TokenResponseDto> reissue(
      @Valid @RequestBody TokenRequestDto.Reissue request) {
    TokenResponseDto response = tokenService.reissueToken(request);
    return ApiGlobalResponse.ok(response);
  }
}
