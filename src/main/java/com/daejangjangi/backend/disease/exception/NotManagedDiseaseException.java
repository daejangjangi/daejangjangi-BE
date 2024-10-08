package com.daejangjangi.backend.disease.exception;

import com.daejangjangi.backend.disease.exception.type.DiseaseErrorType;
import com.daejangjangi.backend.global.exception.ClientDataException;
import lombok.Getter;

@Getter
public class NotManagedDiseaseException extends ClientDataException {

  private final String code;

  public NotManagedDiseaseException() {
    this(DiseaseErrorType.NOT_MANAGED_DISEASE.getMessage());
  }

  public NotManagedDiseaseException(String message) {
    super(message);
    this.code = DiseaseErrorType.NOT_MANAGED_DISEASE.name();
  }
}
