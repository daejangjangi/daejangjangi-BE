package com.daejangjangi.backend.daejangtoon.domain.entity;

import com.daejangjangi.backend.daejangtoon.domain.enums.Yoil;
import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "daejangtoons")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Daejangtoon extends BaseEntity {

  @Builder
  public Daejangtoon(
      Integer chapter,
      String title,
      String overview,
      Yoil yoil
  ) {
    this.chapter = chapter;
    this.title = title;
    this.overview = overview;
    this.hit = 0L;
    this.yoil = yoil;

    toonImages = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_id")
  private Long id;

  @Column(name = "daejangtoon_chapter", columnDefinition = "smallint", nullable = false)
  private Integer chapter;

  @Column(name = "daejangtoon_title", nullable = false)
  private String title;

  @Column(name = "daejangtoon_profile", nullable = false)
  private String profile;

  @Column(name = "daejangtoon_overview", length = 250, nullable = false)
  private String overview;

  @Column(name = "daejangtoon_hit", nullable = false)
  private Long hit;

  @Column(name = "daejangtoon_yoil", nullable = false)
  @Enumerated(EnumType.STRING)
  private Yoil yoil;

  @OneToMany(mappedBy = "daejangtoon", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<DaejangtoonImage> toonImages;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 이미지 업데이트
   *
   * @param profile 프로필 이미지
   */
  public void updateProfile(String profile) {
    this.profile = profile;
  }

  /**
   * 대장툰 이미지 등록
   *
   * @param toonImages 대장툰 이미지 목록
   */
  public void addImages(List<DaejangtoonImage> toonImages) {
    if (Objects.isNull(this.toonImages)) {
      this.toonImages = new ArrayList<>();
    }
    for (DaejangtoonImage toonImage : toonImages) {
      if (toonImage != null && !this.toonImages.contains(toonImage)) {
        this.toonImages.add(toonImage);
        toonImage.updateParent(this);
      }
    }
  }
}
