package com.daejangjangi.backend.member.domain;

import com.daejangjangi.backend.disease.Disease;
import jakarta.persistence.Entity;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members_diseases")
@Entity
public class MemberDisease {

  @Builder
  public MemberDisease(
      Member member,
      Disease disease
  ) {
    this.member = member;
    this.disease = disease;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "disease_id")
  private Disease disease;

  /*-------------Business Logic---------------------------Business Logic---------------------------Business Logic---------------------------Business Logic---------------------------Business Logic--------------*/

  /**
   * 회원 갱신
   *
   * @param member
   */
  public void updateParent(Member member) {
    this.member = member;
  }
}
