package com.daejangjangi.backend.member.domain.entity;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.like.domain.entity.DaejangtoonLike;
import com.daejangjangi.backend.like.domain.entity.PostCommentLike;
import com.daejangjangi.backend.like.domain.entity.PostLike;
import com.daejangjangi.backend.member.domain.enums.Role;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.social.domain.entity.SocialAccount;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "members")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class Member extends BaseEntity {

  @Builder
  public Member(
      String email,
      String password,
      String nickname,
      String gender,
      String profile,
      LocalDate birth,
      boolean serviceUsage,
      boolean personnelInfo,
      boolean sensitiveInfo,
      boolean promotionReception
  ) {
    this.email = email;
    this.password = password;
    this.nickname = nickname;
    this.gender = gender;
    this.profile = profile;
    this.birth = birth;
    this.serviceUsage = serviceUsage;
    this.personnelInfo = personnelInfo;
    this.sensitiveInfo = sensitiveInfo;
    this.promotionReception = promotionReception;
    this.role = Role.MEMBER;

    this.diseases = new ArrayList<>();
    this.categories = new ArrayList<>();
    this.daejangtoonLikes = new ArrayList<>();
    this.socialAccounts = new ArrayList<>();
    this.postLikes = new ArrayList<>();
    this.posts = new ArrayList<>();
    this.postComments = new ArrayList<>();
    this.postCommentLikes = new ArrayList<>();
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;

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

  @Enumerated(EnumType.STRING)
  @Column(name = "member_role", nullable = false)
  private Role role;

  @Column(name = "member_agree_service_usage", nullable = false)
  private boolean serviceUsage;

  @Column(name = "member_agree_personnel_info", nullable = false)
  private boolean personnelInfo;

  @Column(name = "member_agree_sensitive_info", nullable = false)
  private boolean sensitiveInfo;

  @Column(name = "member_agree_promotion_reception")
  private boolean promotionReception;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<MemberDisease> diseases;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<MemberCategory> categories;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<DaejangtoonLike> daejangtoonLikes;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<SocialAccount> socialAccounts;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<PostLike> postLikes;

  @OneToMany(mappedBy = "member")
  private List<Post> posts;

  @OneToMany(mappedBy = "member")
  private List<PostComment> postComments;

  @OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<PostCommentLike> postCommentLikes;

  /*-------------Business Logic---------------------------Business Logic--------------------------*/

  /**
   * 회원 장건강 질환 등록
   *
   * @param diseases 회원 장건강 질환 목록
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
   * 회원 관심 카테고리 등록
   *
   * @param categories 회원 관심 카테고리 목록
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
   * @param encodedPassword 암호화된 비밀번호
   */
  public void encodePassword(String encodedPassword) {
    this.password = encodedPassword;
  }

  /**
   * 회원 정보 수정
   *
   * @param member 회원
   */
  public void updateMember(Member member) {
    if (!Objects.equals(this.nickname, member.nickname)) {
      this.nickname = member.nickname;
    }

    if (!Objects.equals(this.birth, member.birth)) {
      this.birth = member.birth;
    }

    if (!Objects.equals(this.gender, member.gender)) {
      this.gender = member.gender;
    }
  }
}