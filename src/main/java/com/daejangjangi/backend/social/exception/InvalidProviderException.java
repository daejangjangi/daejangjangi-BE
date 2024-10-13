package com.daejangjangi.backend.social.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.social.exception.type.SocialErrorType;
import lombok.Getter;

@Getter
public class InvalidProviderException extends ClientDataException {

  private final String code;

  public InvalidProviderException() {
    this(SocialErrorType.INVALID_PROVIDER_ERROR.getMessage());
  }

  public InvalidProviderException(final String message) {
    super(message);
    this.code = SocialErrorType.INVALID_PROVIDER_ERROR.name();
  }
}
