package com.daejangjangi.backend.post.service;

import com.daejangjangi.backend.fcm.repository.FcmTokenRepository;
import com.daejangjangi.backend.like.domain.entity.PostLike;
import com.daejangjangi.backend.notification.domain.enums.NotificationType;
import com.daejangjangi.backend.notification.repository.NotificationRepository;
import com.daejangjangi.backend.post.repository.PostLikeRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import com.daejangjangi.backend.post.repository.PostLockRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

  private final PostLikeRepository postLikeRepository;
  private final PostLockRepository postLockRepository;
  private final FcmTokenRepository fcmTokenRepository;
  private final NotificationRepository notificationRepository;
  private final String LIKE_NOTIFICATION_BODY = "1명이 공감을 해주었어요.";

  /**
   * named lock 사용한 게시글 좋아요 처리
   *
   * @param member 회원 정보
   * @param post   게시글 정보
   */
  @Transactional
  public void likePostWithLock(Member member, Post post) {
    try {
      postLockRepository.getLock(post.getId().toString());
      like(member, post);
    } finally {
      postLockRepository.releaseLock(post.getId().toString());
    }
  }

  /**
   * 게시글 좋아요 비즈니스 로직
   *
   * @param member 회원 정보
   * @param post   게시글 정보
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void like(Member member, Post post) {
    postLikeRepository.findByMemberAndPost(member, post)
        .ifPresentOrElse(postLike -> {
          post.updateLikeCount(-1);
          postLikeRepository.delete(postLike);
        }, () -> {
          post.updateLikeCount(1);
          postLikeRepository.save(new PostLike(member, post));
          if (!member.equals(post.getMember())) {
            fcmTokenRepository.findByMember(post.getMember()).forEach(fcmToken -> {
              saveLikeNotification(post);
              sendNotification(fcmToken.getFcmToken(), post.getTitle(), LIKE_NOTIFICATION_BODY);
            });
          }
        });
  }

  /**
   * 푸시 알림 전송
   *
   * @param token 대상 디바이스의 FCM 토큰
   * @param title 알림 제목
   * @param body  알림 내용
   * @throws FirebaseMessagingException FCM 전송 중 오류 발생 시
   */
  @Async
  protected void sendNotification(String token, String title, String body) {
    Notification notification = Notification.builder()
        .setTitle(title)
        .setBody(body)
        .build();

    Message message = Message.builder()
        .setToken(token)
        .setNotification(notification)
        .build();

    try {
      FirebaseMessaging.getInstance().send(message);
    } catch (FirebaseMessagingException e) {
      fcmTokenRepository.deleteByFcmToken(token);
    }
  }

  /**
   * 알림 정보 저장
   *
   * @param post 게시글 정보
   */
  private void saveLikeNotification(Post post) {
    com.daejangjangi.backend.notification.domain.entity.Notification notification =
        com.daejangjangi.backend.notification.domain.entity.Notification.builder()
            .receiver(post.getMember())
            .title(post.getTitle())
            .body(LIKE_NOTIFICATION_BODY)
            .contentId(post.getId())
            .type(NotificationType.LIKE).build();
    notificationRepository.save(notification);
  }

}
