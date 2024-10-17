package com.daejangjangi.backend.token.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "tokens")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token extends BaseEntity {

  @Builder
  public Token(
      String memberId,
      String refreshToken
  ) {
    this.memberId = memberId;
    this.refreshToken = refreshToken;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String memberId;
  private String refreshToken;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * refreshToken 변경
   *
   * @param refreshToken
   */
  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
