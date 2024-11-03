package com.daejangjangi.backend.cardnews.domain.entity;

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

@Table(name = "cardnews_images")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardnewsImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cardnews_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cardnews_id", nullable = false)
  private Cardnews cardnews;

  @Column(name = "cardnews_image_key", nullable = false, length = 100)
  private String key;

  @Column(name = "cardnews_image_order", columnDefinition = "tinyint", nullable = false)
  private Integer order;

  @Column(name = "cardnews_image", columnDefinition = "longtext", nullable = false)
  private String image;

  @Builder
  public CardnewsImage(
      String key,
      Integer order,
      String image
  ) {
    this.key = key;
    this.order = order;
    this.image = image;
  }

  public void updateParent(Cardnews cardnews) {
    this.cardnews = cardnews;
  }
}
