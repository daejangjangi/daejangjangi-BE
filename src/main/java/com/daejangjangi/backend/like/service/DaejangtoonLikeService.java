package com.daejangjangi.backend.like.service;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.domain.entity.DaejangtoonChapter;
import com.daejangjangi.backend.like.domain.entity.DaejangtoonLike;
import com.daejangjangi.backend.like.repository.DaejangtoonLikeRepository;
import com.daejangjangi.backend.member.domain.entity.Member;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DaejangtoonLikeService {

  private final DaejangtoonLikeRepository daejangtoonLikeRepository;

  @Transactional
  public void like(Member member, DaejangtoonChapter chapter) {
    Optional<DaejangtoonLike> optionalDaejangtoonLike
        = daejangtoonLikeRepository.findByMemberAndChapter(member, chapter);
    if (optionalDaejangtoonLike.isPresent()) {
      DaejangtoonLike daejangtoonLike = optionalDaejangtoonLike.get();
      chapter.removeLike(daejangtoonLike);
      daejangtoonLikeRepository.delete(daejangtoonLike);
    } else {
      DaejangtoonLike daejangtoonLike = DaejangtoonLike.builder()
          .member(member)
          .chapter(chapter)
          .build();
      chapter.addLike(daejangtoonLike);
      daejangtoonLikeRepository.save(daejangtoonLike);
    }
  }
}
