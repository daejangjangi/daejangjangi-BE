package com.daejangjangi.backend.newsletter.exception;

import com.daejangjangi.backend.global.exception.ClientDataException;
import com.daejangjangi.backend.newsletter.exception.type.NewsletterErrorType;
import lombok.Getter;

@Getter
public class NotFoundNewsletterException extends ClientDataException {

  private final String code;

  public NotFoundNewsletterException() {
    this(NewsletterErrorType.NOT_FOUND_NEWSLETTER.getMessage());
  }

  public NotFoundNewsletterException(String message) {
    super(message);
    this.code = NewsletterErrorType.NOT_FOUND_NEWSLETTER.name();
  }
}
