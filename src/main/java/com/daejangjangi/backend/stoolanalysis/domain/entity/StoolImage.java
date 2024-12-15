package com.daejangjangi.backend.stoolanalysis.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Getter
@Entity
@Table(name = "stool_images")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoolImage extends BaseEntity {

  @Builder
  public StoolImage(
      String stoolImage,
      StoolDiagnosis stooldiagnosis

  ) {
    this.stoolImage = stoolImage;
    this.stooldiagnosis = stooldiagnosis;
  }

  @Id
  @Column(name = "stool_image_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stool_image", nullable = false)
  private String stoolImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stool_diagnosis_id")
  @JsonIgnore
  private StoolDiagnosis stooldiagnosis;

  public void updateStoolDiagnosis(StoolDiagnosis stooldiagnosis) {
    this.stooldiagnosis = stooldiagnosis;
  }
}
