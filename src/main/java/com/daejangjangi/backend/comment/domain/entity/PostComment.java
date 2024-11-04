package com.daejangjangi.backend.comment.domain.entity;

import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.like.domain.entity.PostCommentLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@Table(name = "posts_comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment extends BaseEntity {

  @Builder
  private PostComment(
      Member member,
      Post post,
      String content,
      PostComment parent
  ) {
    this.member = member;
    this.post = post;
    this.content = content;
    this.parent = parent;
    this.isDeleted = false;
    this.children = new ArrayList<>();
    this.likes = new ArrayList<>();
  }

  @Id
  @Column(name = "post_comment_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.SET_NULL)
  private Member member;

  @JoinColumn(name = "post_id")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  private Post post;

  @Column(name = "comment_content", length = 400)
  private String content;

  @JoinColumn(name = "parent_comment_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private PostComment parent;

  @OneToMany(mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<PostComment> children;

  @Column(name = "comment_deleted")
  private boolean isDeleted;

  @OneToMany(mappedBy = "postComment", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<PostCommentLike> likes;

  public void updateParent(PostComment parent) {
    this.parent = parent;
  }

  public void updateContent(String content) {
    if (!this.content.equals(content)) {
      this.content = content;
    }
  }

  public void updateDeleted() {
    this.isDeleted = true;
  }

  public void addPostCommentLike(PostCommentLike postCommentLike) {
    if (Objects.isNull(this.likes)) {
      this.likes = new ArrayList<>();
    }
    this.likes.add(postCommentLike);
  }

  public void removePostCommentLike(PostCommentLike postCommentLike) {
    if (Objects.isNull(this.likes)) {
      this.likes = new ArrayList<>();
    }
    this.likes.remove(postCommentLike);
  }

  public void addChildComment(PostComment child) {
    if (Objects.isNull(this.children)) {
      this.children = new ArrayList<>();
    }
    this.children.add(child);
  }

}
