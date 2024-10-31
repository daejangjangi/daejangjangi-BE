package com.daejangjangi.backend.daejangtoon.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

@Table(name = "daejangtoons_chapters")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DaejangtoonChapter extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_chapter_id")
  private Long id;

  @Column(name = "daejangtoon_chapter", nullable = false)
  private Integer chapter;

  @Column(name = "daejangtoon_chapter_title", nullable = false)
  private String title;

  @Column(name = "daejangtoon_chapter_profile", columnDefinition = "longtext", length = 2000, nullable = false)
  private String profile;

  @Column(name = "daejangtoon_chapter_hit", nullable = false)
  private Long hit;

  @OneToMany(mappedBy = "daejangtoon", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<DaejangtoonImage> toonImages;

  @Builder
  public DaejangtoonChapter(
      Integer chapter,
      String title,
      String profile
  ) {
    this.chapter = chapter;
    this.title = title;
    this.profile = profile;
    this.hit = 0L;

    this.toonImages = new ArrayList<>();
  }

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
