package com.daejangjangi.backend.daejangtoon.domain.entity;

import com.daejangjangi.backend.daejangtoon.domain.enums.Yoil;
import com.daejangjangi.backend.global.common.BaseEntity;
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
      String title,
      String overview,
      Yoil yoil
  ) {
    this.title = title;
    this.overview = overview;
    this.yoil = yoil;

    this.chapters = new ArrayList<>();
  }

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

  @OneToMany(mappedBy = "daejangtoon")
  private List<DaejangtoonChapter> chapters;
}
