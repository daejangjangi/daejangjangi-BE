package com.daejangjangi.backend.daejangtoon.service;

import com.daejangjangi.backend.daejangtoon.domain.entity.Daejangtoon;
import com.daejangjangi.backend.daejangtoon.exception.NotFoundToonException;
import com.daejangjangi.backend.daejangtoon.repository.DaejangtoonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DaejangtoonService {

  private final DaejangtoonRepository daejangtoonRepository;

  /**
   * 대장툰 저장
   *
   * @param daejangtoon 대장툰
   * @return 대장툰 ID
   */
  @Transactional
  public Long save(Daejangtoon daejangtoon) {
    return daejangtoonRepository.save(daejangtoon).getId();
  }

  /**
   * 대장툰 조회
   *
   * @param daejangtoonId 대장툰 ID
   * @return Daejangtoon
   */
  public Daejangtoon findById(Long daejangtoonId) {
    return daejangtoonRepository.findById(daejangtoonId).orElseThrow(NotFoundToonException::new);
  }
}
