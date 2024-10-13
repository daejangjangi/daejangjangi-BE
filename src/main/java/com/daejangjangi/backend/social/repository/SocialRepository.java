package com.daejangjangi.backend.social.repository;

import com.daejangjangi.backend.social.domain.entity.SocialAccount;
import com.daejangjangi.backend.social.domain.enums.SocialAccountProvider;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepository extends JpaRepository<SocialAccount, Long> {

  Optional<SocialAccount> findBySnsIdAndProvider(String snsId, SocialAccountProvider provider);
}
