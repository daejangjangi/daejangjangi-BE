package com.daejangjangi.backend.faq.domain.entity;

import com.daejangjangi.backend.faq.domain.enums.FaqCategory;
import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
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

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "faqs")
@Entity
public class Faq extends BaseEntity {

  @Builder
  public Faq(
      FaqCategory category,
      String question
  ) {
    this.category = category;
    this.question = question;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "faq_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(name = "faq_category", nullable = false)
  @Enumerated(EnumType.STRING)
  private FaqCategory category;

  @Column(name = "faq_question", columnDefinition = "longtext", length = 500, nullable = false)
  private String question;

  @Column(name = "faq_answer", columnDefinition = "longtext", length = 10000)
  private String answer;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 회원 갱신
   *
   * @param member 회원
   */
  public void updateParent(Member member) {
    this.member = member;
  }
}
