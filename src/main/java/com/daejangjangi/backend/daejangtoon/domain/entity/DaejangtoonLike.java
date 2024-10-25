package com.daejangjangi.backend.daejangtoon.domain.entity;

import com.daejangjangi.backend.member.domain.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "daejangtoons_likes")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DaejangtoonLike {

  public DaejangtoonLike(
      Member member,
      Daejangtoon daejangtoon
  ) {
    this.member = member;
    this.daejangtoon = daejangtoon;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_like_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "daejangtoon_id")
  private Daejangtoon daejangtoon;
}
