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