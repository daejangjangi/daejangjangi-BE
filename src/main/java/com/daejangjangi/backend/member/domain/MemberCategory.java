package com.daejangjangi.backend.member.domain;

import com.daejangjangi.backend.category.Category;
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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members_categories")
@Entity
public class MemberCategory {

  @Builder
  public MemberCategory(
      Member member,
      Category category
  ) {
    this.member = member;
    this.category = category;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_category_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id")
  private Category category;

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
