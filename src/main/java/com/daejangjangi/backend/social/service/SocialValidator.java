package com.daejangjangi.backend.social.service;

import com.daejangjangi.backend.social.domain.enums.SocialAccountProvider;
import com.daejangjangi.backend.social.exception.InvalidProviderException;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class SocialValidator {

  public void checkSocialAccountProvider(String provider) {
    if (!StringUtils.hasText(provider) || Arrays.stream(SocialAccountProvider.values())
        .noneMatch(type -> type.name().equals(provider.toUpperCase()))) {
      throw new InvalidProviderException();
    }
  }
}