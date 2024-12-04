package com.daejangjangi.backend.stoolanalysis.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoolanalysis.domain.enums.DietType;
import com.daejangjangi.backend.stoolanalysis.domain.enums.Mucus;
import com.daejangjangi.backend.stoolanalysis.domain.enums.ProteinLumps;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "stool_ diagnoses")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StoolDiagnosis extends BaseEntity {

  @Builder
  public StoolDiagnosis(
      Color color,
      Form form,
      DietType dietType,
      ProteinLumps proteinLumps,
      Mucus mucus,
      boolean isBloody,
      String bloodyStoolDescription,
      String additionalDescription,
      String dietDescription,
      Member member,
      LocalDateTime stoolAt,
      String diagnosisDescription
  ) {
    this.color = color;
    this.form = form;
    this.dietType = dietType;
    this.proteinLumps = proteinLumps;
    this.mucus = mucus;
    this.isBloody = isBloody;
    this.bloodyStoolDescription = bloodyStoolDescription;
    this.additionalDescription = additionalDescription;
    this.dietDescription = dietDescription;
    this.member = member;
    this.stoolAt = stoolAt;
    this.diagnosisDescription = diagnosisDescription;
    this.stoolImages = new ArrayList<>();
  }

  @Id
  @Column(name = "stool_diagnosis_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "stool_color", nullable = false)
  private Color color;

  @Column(name = "stool_form", nullable = false)
  private Form form;

  @Column(name = "stool_diet_type", nullable = false)
  private DietType dietType;

  @Column(name = "stool_protein_lumps", nullable = false)
  private ProteinLumps proteinLumps;

  @Column(name = "stool_mucus", nullable = false)
  private Mucus mucus;

  @Column(name = "stool_bloody", nullable = false)
  private boolean isBloody;

  @Column(name = "bloody_stool_Description", length = 500)
  private String bloodyStoolDescription;

  @Column(name = "stool_additional_description", length = 500)
  private String additionalDescription;

  @Column(name = "diet_description", length = 500)
  private String dietDescription;

  @Column(name = "stool_diagnosis_Description", length = 10000, nullable = false)
  private String diagnosisDescription;

  @Column(name = "stool_at", nullable = false)
  private LocalDateTime stoolAt;

  @OneToMany(mappedBy = "stooldiagnosis", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<StoolImage> stoolImages;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "stool_log_id")
  private Stoollog stoollog;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  public void updateStoolog(Stoollog stoollog) {
    this.stoollog = stoollog;
  }

  public void addStoolImages(List<StoolImage> images) {
    if (this.stoolImages == null) {
      this.stoolImages = new ArrayList<>();
    }

    for (StoolImage image : images) {
      this.stoolImages.add(image);
    }
  }

}
