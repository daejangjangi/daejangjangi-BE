package com.daejangjangi.backend.stoollog.domain.entity;


import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoollog.domain.enums.Color;
import com.daejangjangi.backend.stoollog.domain.enums.Form;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stool_logs")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Stoollog extends BaseEntity {

  @Builder
  public Stoollog(
      Long id,
      Color color,
      Form form,
      LocalDateTime loggedAt
  ) {
    this.id = (id == null) ? null : id;
    this.color = color;
    this.form = form;
    this.loggedAt = loggedAt;
  }

  @Id
  @Column(name = "stool_log_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Enumerated(EnumType.STRING)
  @Column(name = "stool_color")
  private Color color;

  @Enumerated(EnumType.STRING)
  @Column(name = "stool_form")
  private Form form;

  @Column(name = "stool_logged_at")
  private LocalDateTime loggedAt;

  public void updateColor(Color color) {
    if (!this.color.equals(color)) {
      this.color = color;
    }
  }

  public void updateForm(Form form) {
    if (!this.form.equals(form)) {
      this.form = form;
    }
  }

  public void updateLoggedAt(LocalDateTime loggedAt) {
    if (!this.loggedAt.equals(loggedAt)) {
      this.loggedAt = loggedAt;
    }
  }

  public void updateMember(Member member) {
    this.member = member;
  }


}
