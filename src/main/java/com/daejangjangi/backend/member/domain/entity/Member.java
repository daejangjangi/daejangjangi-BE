package com.daejangjangi.backend.member.domain.entity;

import com.daejangjangi.backend.member.domain.dto.MemberResponseDto;
import com.daejangjangi.backend.member.domain.enums.Role;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "members")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  private static final String SNS_ID_DEFAULT = "NotOauth";

  @Builder
  public Member(
      String snsId,
      String email,
      String password,
      String nickname,
      String gender,
      String profile,
      LocalDate birth
  ) {
    this.snsId = snsId != null ? snsId : SNS_ID_DEFAULT;
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.gender = gender;
    this.profile = profile;
    this.birth = birth;
    this.role = Role.MEMBER;

    this.diseases = new ArrayList<>();
    this.categories = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

  @Column(name = "member_sns_id", length = 100, nullable = false)
  private String snsId;

  @Column(name = "member_email", length = 50, nullable = false, unique = true)
  private String email;

  @Column(name = "member_password", length = 100, nullable = false)
  private String password;

  @Column(name = "member_nickname", length = 5, nullable = false, unique = true)
  private String nickname;

  @Column(name = "member_gender", length = 1, nullable = false)
  private String gender;

  @Column(name = "member_birth", nullable = false)
  private LocalDate birth;

  @Column(name = "member_profile")
  private String profile;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Enumerated(EnumType.STRING)
  private Role role;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<MemberDisease> diseases;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<MemberCategory> categories;

  /*-------------Business Logic---------------------------Business Logic------------------------*/

  /**
   * note : 회원 탈퇴 처리 상의
   */
  public void withdraw() {
    this.deletedAt = LocalDateTime.now();
  }

  /**
   * 회원 장건강 질환 등록
   *
   * @param diseases
   */
  public void addDiseases(List<MemberDisease> diseases) {
    if (Objects.isNull(this.diseases)) {
      this.diseases = new ArrayList<>();
    }
    for (MemberDisease disease : diseases) {
      if (disease != null && !this.diseases.contains(disease)) {
        this.diseases.add(disease);
        disease.updateParent(this);
      }
    }
  }

  /**
   * 회원 관심 상품 카테고리 등록
   *
   * @param categories
   */
  public void addCategories(List<MemberCategory> categories) {
    if (Objects.isNull(this.categories)) {
      this.categories = new ArrayList<>();
    }
    for (MemberCategory category : categories) {
      if (category != null && !this.categories.contains(category)) {
        this.categories.add(category);
        category.updateParent(this);
      }
    }
  }

  /**
   * 비밀번호 암호화
   *
   * @param encodedPassword
   */
  public void encodePassword(String encodedPassword) {
    this.password = encodedPassword;
  }


  /**
   * Dto 변환
   *
   * @return MemberResponseDto.Info
   */
  public MemberResponseDto.Info toDto() {
    List<String> disease = this.diseases.stream().map(e -> e.getDisease().getName()).toList();
    List<String> categories = this.categories.stream().map(e -> e.getCategory().getName()).toList();
    return new MemberResponseDto.Info(nickname, birth, gender, disease, categories);
  }
}