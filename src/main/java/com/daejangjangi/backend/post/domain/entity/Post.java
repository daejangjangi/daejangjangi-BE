package com.daejangjangi.backend.post.domain.entity;

import com.daejangjangi.backend.board.domain.entity.BoardPost;
import com.daejangjangi.backend.global.common.BaseEntity;
import com.daejangjangi.backend.member.domain.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "posts")
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

  public Post(
      String title,
      String content
  ) {
    this.title = title;
    this.content = content;
    this.hit = 0L;
    this.boards = new ArrayList<>();
  }

  public Post(
      Long id,
      String title,
      String content
  ) {
    this.id = id;
    this.title = title;
    this.content = content;
  }

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "member_id")
  private Member member;

  @Column(name = "post_title", length = 100, nullable = false)
  private String title;

  @Column(name = "post_content", columnDefinition = "longtext", length = 10000, nullable = false)
  private String content;

  @Column(name = "post_hit")
  private Long hit;

  @OneToMany(mappedBy = "post", orphanRemoval = true, cascade = CascadeType.ALL)
  private List<BoardPost> boards;

  public void updateMember(Member member) {
    this.member = member;
  }

  public void addBoards(List<BoardPost> boards) {
    if (Objects.isNull(this.boards)) {
      this.boards = new ArrayList<>();
    }
    for (BoardPost board : boards) {
      if (board != null && !this.boards.contains(board)) {
        this.boards.add(board);
      }
    }
  }

  public void updatePost(Post post) {
    if (!Objects.equals(post.title, this.title)) {
      this.title = post.title;
    }

    if (!Objects.equals(post.content, this.content)) {
      this.content = post.content;
    }
  }
}
