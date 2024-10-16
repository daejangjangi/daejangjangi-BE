package com.daejangjangi.backend.token.repository;

import com.daejangjangi.backend.token.domain.entity.Token;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

  Optional<Token> findByRefreshToken(String refreshToken);

  Optional<Token> findByMemberId(String memberId);
}
