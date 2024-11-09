package com.daejangjangi.backend.reels.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "reels")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reels extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reels_id")
  private Long id;

  @Column(name = "reels_title", nullable = false)
  private String title;

  @Column(name = "reels_video", columnDefinition = "longtext", length = 2000, nullable = false)
  private String reelsVideo;

  @Builder
  public Reels(
      String title
  ) {
    this.title = title;
  }

  public void updateVideo(String reelsVideo) {
    this.reelsVideo = reelsVideo;
  }
}
