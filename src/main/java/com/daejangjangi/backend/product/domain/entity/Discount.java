package com.daejangjangi.backend.product.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "discounts")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "discount_id")
  private Long id;

  @Column(name = "discount_name", nullable = false, unique = true)
  private String name;

  @Column(name = "discount_rate", columnDefinition = "tinyint", nullable = false)
  private Integer rate;

  @Builder
  public Discount(
      String name,
      Integer rate
  ) {
    this.name = name;
    this.rate = rate;
  }
}
