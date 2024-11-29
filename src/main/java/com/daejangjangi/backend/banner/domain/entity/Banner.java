package com.daejangjangi.backend.banner.domain.entity;

import com.daejangjangi.backend.product.domain.entity.Product;
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

@Table(name = "banners")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Banner {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "banner_id")
  private Long id;

  @Column(name = "banner_image", length = 1000, nullable = false)
  private String image;

  @OneToOne
  @JoinColumn(name = "product_id", nullable = false, unique = true,
      foreignKey = @ForeignKey(
          value = ConstraintMode.CONSTRAINT,
          foreignKeyDefinition = "FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE"
      )
  )
  private Product product;

  @Builder
  public Banner(
      String image,
      Product product
  ) {
    this.image = image;
    this.product = product;
  }
}
