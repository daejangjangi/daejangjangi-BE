package com.daejangjangi.backend.daejangtoon.domain.entity;

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
public class DaejangtoonImage {

  @Builder
  public DaejangtoonImage(
      Integer order,
      String image
  ) {
    this.order = order;
    this.image = image;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_image_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "daejangtoon_id", nullable = false)
  private Daejangtoon daejangtoon;

  @Column(name = "daejangtoon_image_order", columnDefinition = "tinyint", nullable = false)
  private Integer order;

  @Column(name = "daejangtoon_image", nullable = false)
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
   * @param daejangtoon 대장툰
   */
  public void updateParent(Daejangtoon daejangtoon) {
    this.daejangtoon = daejangtoon;
  }
}
