package com.daejangjangi.backend.faq.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.qna.domain.entity.Qna;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "faqs")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Faq extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "faq_id")
  private Long id;

  @OneToOne
  @JoinColumn(
      name = "qna_id", nullable = false,
      foreignKey = @ForeignKey(
          value = ConstraintMode.CONSTRAINT,
          foreignKeyDefinition = "FOREIGN KEY (qna_id) REFERENCES qnas(qna_id) ON DELETE CASCADE")
  )
  private Qna qna;

  @Builder
  public Faq(Qna qna) {
    this.qna = qna;
  }
}
