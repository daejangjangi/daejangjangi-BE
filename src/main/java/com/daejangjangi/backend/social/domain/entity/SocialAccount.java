package com.daejangjangi.backend.social.domain.entity;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.social.domain.enums.SocialAccountProvider;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "social_accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SocialAccount {

  @Builder
  public SocialAccount(
      String snsId,
      SocialAccountProvider provider
  ) {
    this.snsId = snsId;
    this.provider = provider;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "social_account_id")
  private Long id;

  @Column(name = "sns_id", unique = true, nullable = false)
  private String snsId;

  @Column(name = "social_account_provider", nullable = false)
  @Enumerated(EnumType.STRING)
  private SocialAccountProvider provider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 회원 갱신
   *
   * @param member
   */
  public void updateParent(Member member) {
    this.member = member;
  }
}
