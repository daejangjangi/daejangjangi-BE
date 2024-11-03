package com.daejangjangi.backend.like.domain.entity;

import com.daejangjangi.backend.comment.domain.entity.PostComment;
import com.daejangjangi.backend.member.domain.entity.Member;
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

@Entity
@Getter
@Table(name = "posts_comments_likes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostCommentLike {

  @Id
  @Column(name = "post_comment_like_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "post_comment_id")
  private PostComment postComment;


  @Builder
  public PostCommentLike(
      Member member,
      PostComment postComment
  ) {
    this.member = member;
    this.postComment = postComment;
  }


}
