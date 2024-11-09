package com.daejangjangi.backend.product.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "discounts")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "dicount_id")
  private Long id;

  @Column(name = "discount_name", nullable = false)
  private String name;

  @Column(name = "dicount_rate", nullable = false)
  private String rate;
}
