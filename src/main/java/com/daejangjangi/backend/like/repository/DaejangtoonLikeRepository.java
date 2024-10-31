package com.daejangjangi.backend.like.repository;

import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.like.domain.entity.DaejangtoonLike;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DaejangtoonLikeRepository extends JpaRepository<DaejangtoonLike, Long> {

  Optional<DaejangtoonLike> findByMemberAndChapter(Member member, DaejangtoonChapter chapter);
}
