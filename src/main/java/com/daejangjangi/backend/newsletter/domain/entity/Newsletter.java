package com.daejangjangi.backend.newsletter.domain.entity;

import com.daejangjangi.backend.category.domain.Category;
import com.daejangjangi.backend.global.common.BaseEntity;
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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Table(name = "newsletters")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Newsletter extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "newsletter_id")
  private Long id;

  @Column(name = "newsletter_title", nullable = false)
  private String title;

  @Column(name = "newsletter_sub_title", nullable = false)
  private String subTitle;

  @Column(name = "newsletter_description", columnDefinition = "longtext", nullable = false)
  private String description;

  @Column(name = "newsletter_profile", columnDefinition = "longtext", length = 2000, nullable = false)
  private String profileImage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "category_id", nullable = false)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Category category;

  @Builder
  public Newsletter(
      String title,
      String subTitle,
      String description
  ) {
    this.title = title;
    this.subTitle = subTitle;
    this.description = description;
  }

  public void updateProfile(String profileUrl) {
    this.profileImage = profileUrl;
  }

  public void updateCategory(Category category) {
    this.category = category;
  }
}
