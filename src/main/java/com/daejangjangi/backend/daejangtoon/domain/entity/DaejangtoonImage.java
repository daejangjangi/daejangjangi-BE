package com.daejangjangi.backend.daejangtoon.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
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

@Table(name = "daejangtoons_images")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DaejangtoonImage extends BaseEntity {

  @Builder
  public DaejangtoonImage(
      String key,
      Integer order,
      String image
  ) {
    this.key = key;
    this.order = order;
    this.image = image;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "daejangtoon_chapter_id", nullable = false)
  private DaejangtoonChapter chapter;

  @Column(name = "daejang_image_key", length = 100, nullable = false)
  private String key;

  @Column(name = "daejangtoon_image_order", columnDefinition = "tinyint", nullable = false)
  private Integer order;

  @Column(name = "daejangtoon_image", length = 1000, nullable = false)
  private String image;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 이미지 업데이트
   *
   * @param image 대장툰 이미지
   */
  public void updateImage(String image) {
    this.image = image;
  }

  /**
   * 대장툰 갱신
   *
   * @param chapter 대장툰 회차
   */
  public void updateParent(DaejangtoonChapter chapter) {
    this.chapter = chapter;
  }
}
