package com.daejangjangi.backend.product.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "products")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long id;

  @Column(name = "product_name", nullable = false)
  private String name;

  @Column(name = "product_comment", nullable = false)
  private String comment;

  @Column(name = "product_sale_link", nullable = false)
  private String saleLink;

  @Column(name = "product_regular_price", nullable = false)
  private Long regularPrice;

  @Column(name = "product_profile", nullable = false)
  private String profile;

  @Column(name = "product_hit", nullable = false)
  private Long hit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  private Discount discount;

  @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ProductCategory> categories;

  @Builder
  public Product(
      String name,
      String comment,
      String saleLink,
      Long regularPrice
  ) {
    this.name = name;
    this.comment = comment;
    this.saleLink = saleLink;
    this.regularPrice = regularPrice;

    this.hit = 0L;
  }

  public void addCategories(List<ProductCategory> categories) {
    if (Objects.isNull(this.categories)) {
      this.categories = new ArrayList<>();
    }
    for (ProductCategory category : categories) {
      if (category != null && !this.categories.contains(category)) {
        this.categories.add(category);
        category.updateParent(this);
      }
    }
  }

  public void updateProfile(String profile) {
    this.profile = profile;
  }

  public void discount(Discount discount) {
    this.discount = discount;
  }
}
