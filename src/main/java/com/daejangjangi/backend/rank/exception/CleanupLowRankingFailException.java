package com.daejangjangi.backend.rank.exception;

import com.daejangjangi.backend.global.exception.ServerDataException;
import com.daejangjangi.backend.rank.exception.type.RankErrorType;
import lombok.Getter;

@Getter
public class CleanupLowRankingFailException extends ServerDataException {

  private final String code;

  public CleanupLowRankingFailException() {
    this(RankErrorType.CLEANUP_FAIL.getMessage());
  }

  public CleanupLowRankingFailException(final String message) {
    super(message);
    this.code = RankErrorType.CLEANUP_FAIL.name();
  }
}
