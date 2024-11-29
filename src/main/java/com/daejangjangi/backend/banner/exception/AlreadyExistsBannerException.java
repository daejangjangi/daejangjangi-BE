package com.daejangjangi.backend.banner.exception;

import com.daejangjangi.backend.banner.exception.type.BannerErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class AlreadyExistsBannerException extends ClientDataException {

  private final String code;

  public AlreadyExistsBannerException() {
    this(BannerErrorType.ALREADY_EXISTS_BANNER.getMessage());
  }

  public AlreadyExistsBannerException(final String message) {
    super(message);
    this.code = BannerErrorType.ALREADY_EXISTS_BANNER.name();
  }
}
