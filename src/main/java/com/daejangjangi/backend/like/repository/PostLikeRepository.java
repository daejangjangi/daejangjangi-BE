package com.daejangjangi.backend.like.repository;

import com.daejangjangi.backend.like.domain.entity.PostLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.post.domain.entity.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  Optional<PostLike> findByMemberAndPost(Member member, Post post);

}
