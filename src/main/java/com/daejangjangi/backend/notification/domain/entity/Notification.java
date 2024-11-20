package com.daejangjangi.backend.notification.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.notification.domain.enums.NotificationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
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

@Getter
@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

  @Builder
  public Notification(
      Member receiver,
      Long contentId,
      NotificationType type,
      String title,
      String body
  ) {
    this.receiver = receiver;
    this.type = type;
    this.title = title;
    this.body = body;
    this.contentId = contentId;
    this.read = false;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "notification_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_receiver_id", nullable = false)
  private Member receiver;

  @Column(name = "notification_title", nullable = false)
  private String title;

  @Column(name = "notification_body", nullable = false)
  private String body;

  @Column(name = "notification_read", nullable = false)
  private boolean read;

  @Column(name = "notification_read_at")
  private LocalDateTime readAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "notification_type", nullable = false)
  private NotificationType type;

  @Column(name = "notification_content_id", nullable = false)
  private Long contentId;

  public void updateReadStatus() {
    this.read = true;
    this.readAt = LocalDateTime.now();
  }
}
