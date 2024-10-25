package com.daejangjangi.backend.social.service;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.social.domain.entity.SocialAccount;
import com.daejangjangi.backend.social.domain.enums.SocialAccountProvider;
import com.daejangjangi.backend.social.repository.SocialRepository;
import com.daejangjangi.backend.token.domain.dto.TokenResponseDto;
import com.daejangjangi.backend.token.service.TokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialService {

  private final SocialRepository socialRepository;
  private final TokenService tokenService;

  /**
   * 소셜 계정 연동 확인
   *
   * @param snsId    소셜 계정 고유값
   * @param provider 소셜 계정 제공자
   * @return TokenResponseDto
   */
  @Transactional
  public TokenResponseDto checkSocialAccountLinkage(String snsId, String provider) {
    Optional<SocialAccount> optional = socialRepository.findBySnsIdAndProvider(snsId,
        SocialAccountProvider.valueOf(provider));
    if (optional.isPresent()) {
      SocialAccount socialAccount = optional.get();
      Member member = socialAccount.getMember();
      return tokenService.getToken(member);
    }
    return null;
  }

  /**
   * 소셜 계정 연동
   *
   * @param snsId    소셜 계정 고유값
   * @param provider 소셜 계정 제공자
   * @param member   회원
   * @return TokenResponseDto
   */
  @Transactional
  public TokenResponseDto linkAccount(String snsId, String provider, Member member) {
    SocialAccount socialAccount = SocialAccount.builder()
        .snsId(snsId)
        .provider(SocialAccountProvider.valueOf(provider))
        .build();
    socialAccount.updateParent(member);
    socialRepository.save(socialAccount);
    return tokenService.getToken(member);
  }
}