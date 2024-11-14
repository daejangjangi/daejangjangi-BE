package com.daejangjangi.backend.post.repository;

import com.daejangjangi.backend.like.domain.entity.PostLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  Optional<PostLike> findByMemberAndPost(Member member, Post post);

  @Query("SELECT pl.post FROM PostLike pl where pl.member = :member")
  List<Post> findPostsByMember(@Param("member") Member member);

}
