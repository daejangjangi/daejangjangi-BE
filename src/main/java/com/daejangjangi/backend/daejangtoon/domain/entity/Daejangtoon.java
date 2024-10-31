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

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "daejangtoon_id")
  private Long id;

  @Column(name = "daejangtoon_title", nullable = false)
  private String title;

  @Column(name = "daejangtoon_overview", length = 250, nullable = false)
  private String overview;

  @Column(name = "daejangtoon_yoil", nullable = false)
  @Enumerated(EnumType.STRING)
  private Yoil yoil;

  @OneToMany(mappedBy = "daejangtoon", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<DaejangtoonChapter> chapters;

  @Builder
  public Daejangtoon(
      String title,
      String overview,
      Yoil yoil
  ) {
    this.title = title;
    this.overview = overview;
    this.yoil = yoil;

    this.chapters = new ArrayList<>();
  }

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 대장툰 회차 등록
   *
   * @param chapter 대장툰 회차
   */
  public void addChapter(DaejangtoonChapter chapter) {
    if (Objects.isNull(this.chapters)) {
      this.chapters = new ArrayList<>();
    }
    if (chapter != null && !this.chapters.contains(chapter)) {
      this.chapters.add(chapter);
      chapter.updateParent(this);
    }
  }
}
