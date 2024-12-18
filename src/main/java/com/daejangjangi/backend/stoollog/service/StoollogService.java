package com.daejangjangi.backend.stoollog.service;

import com.daejangjangi.backend.member.domain.entity.Member;
import com.daejangjangi.backend.stoollog.domain.entity.Stoollog;
import com.daejangjangi.backend.stoollog.exception.NotAuthorizedStoollogException;
import com.daejangjangi.backend.stoollog.exception.NotFoundStoollogException;
import com.daejangjangi.backend.stoollog.repository.StoollogRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoollogService {

  private final StoollogRepository stoolLogRepository;

  /**
   * 배변 일지 등록
   *
   * @param stoolLog 배변 일지 정보
   * @return Stoollog
   */
  public Stoollog save(Stoollog stoolLog) {
    return stoolLogRepository.save(stoolLog);
  }

  /**
   * 배변 일지 수정
   *
   * @param member   회원 정보
   * @param stoolLog 수정된 배변 일지 정보
   */
  @Transactional
  public void update(Member member, Stoollog stoolLog) {
    Stoollog origin = findById(stoolLog.getId());
    validAuthor(member, origin);
    origin.updateColor(stoolLog.getColor());
    origin.updateForm(stoolLog.getForm());
    origin.updateLoggedAt(stoolLog.getLoggedAt());
  }

  /**
   * id로 배변 일지 삭제
   *
   * @param member     회원 정보
   * @param stoolLogId 배변 일지 id
   */
  @Transactional
  public void delete(Member member, Long stoolLogId) {
    Stoollog stoollog = findById(stoolLogId);
    validAuthor(member, stoollog);
    stoolLogRepository.delete(stoollog);
  }

  /**
   * 일별로 배변 일지 조회
   *
   * @param member 회원 정보
   * @param date   조회 날짜
   * @return List StoollogResponse
   */
  public List<Stoollog> getStoollogs(Member member, LocalDate date) {
    LocalDateTime startOfDay = date.atStartOfDay();
    LocalDateTime endOfDay = date.atTime(LocalTime.of(23, 59, 59));
    return stoolLogRepository.findByMemberAndLoggedAtBetweenOrderByLoggedAtAsc(member, startOfDay,
        endOfDay);
  }

  /**
   * 배변 일지 권한 확인
   *
   * @param member   회원 정보
   * @param stoollog 배변 일지 정보
   */
  private void validAuthor(Member member, Stoollog stoollog) {
    if (!member.equals(stoollog.getMember())) {
      throw new NotAuthorizedStoollogException();
    }
  }

  /**
   * id로 배변 일지 찾기
   *
   * @param stoolLogId 배변 일지 Id
   * @return Stoollog
   */
  private Stoollog findById(Long stoolLogId) {
    return stoolLogRepository.findById(stoolLogId).orElseThrow(NotFoundStoollogException::new);
  }
}
