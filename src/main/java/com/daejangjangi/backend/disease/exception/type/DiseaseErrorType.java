package com.daejangjangi.backend.disease.exception.type;

import lombok.Getter;

@Getter
public enum DiseaseErrorType {

  NOT_MANAGED_DISEASE("관리되지 않는 질병입니다.");

  private final String message;

  DiseaseErrorType(String message) {
    this.message = message;
  }
}
