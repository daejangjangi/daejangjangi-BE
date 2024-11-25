package com.daejangjangi.backend.stoollog.service;

import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.repository.StoollogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoollogService {

  private final StoollogRepository stoolLogRepository;

  public Stoollog save(Stoollog stoolLog) {
    return stoolLogRepository.save(stoolLog);
  }

  @Transactional
  public void update(Stoollog stoolLog) {
    // TODO 찾을 수 없다는 에러 추가
    Stoollog origin = stoolLogRepository.findById(stoolLog.getId()).orElseThrow();
    origin.updateColor(stoolLog.getColor());
    origin.updateForm(stoolLog.getForm());
  }

  @Transactional
  public void delete(Long stoolLogId) {
    stoolLogRepository.deleteById(stoolLogId);
  }

}
