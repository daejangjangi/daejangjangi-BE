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

  @Column(name = "product_sale_link", length = 1000, nullable = false)
  private String saleLink;

  @Column(name = "product_regular_price", nullable = false)
  private Integer regularPrice;

  @Column(name = "product_profile", length = 1000, nullable = false)
  private String profile;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "discount_id")
  private Discount discount;

  @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ProductDisease> diseases;

  @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<ProductCategory> categories;

  @Builder
  public Product(
      String name,
      String comment,
      String saleLink,
      Integer regularPrice
  ) {
    this.name = name;
    this.comment = comment;
    this.saleLink = saleLink;
    this.regularPrice = regularPrice;
  }

  public void addDiseases(List<ProductDisease> productDiseases) {
    if (Objects.isNull(this.diseases)) {
      this.diseases = new ArrayList<>();
    }
    for (ProductDisease disease : productDiseases) {
      if (disease != null && !this.diseases.contains(disease)) {
        this.diseases.add(disease);
        disease.updateParent(this);
      }
    }
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
