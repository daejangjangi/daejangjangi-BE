package com.daejangjangi.backend.cardnews.domain.entity;

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

@Table(name = "cardnews")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cardnews extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "cardnews_id")
  private Long id;

  @Column(name = "cardnews_title", nullable = false, length = 100)
  private String title;

  @Column(name = "cardnews_profile", columnDefinition = "longtext", nullable = false)
  private String profile;

  @OneToMany(mappedBy = "cardnews", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<CardnewsImage> newsImages;

  @Builder
  public Cardnews(
      String title
  ) {
    this.title = title;
  }

  public void updateProfile(String profile) {
    this.profile = profile;
  }

  public void addImages(List<CardnewsImage> newsImages) {
    if (Objects.isNull(this.newsImages)) {
      this.newsImages = new ArrayList<>();
    }
    for (CardnewsImage newsImage : newsImages) {
      if (newsImage != null && !this.newsImages.contains(newsImage)) {
        this.newsImages.add(newsImage);
        newsImage.updateParent(this);
      }
    }
  }
}
