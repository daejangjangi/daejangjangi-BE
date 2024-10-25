package com.daejangjangi.backend.global.annotation.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

  private ValidEnum annotation;

  @Override
  public void initialize(ValidEnum constraintAnnotation) {
    this.annotation = constraintAnnotation;
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    boolean result = false;
    Object[] enumValues = this.annotation.enumClass().getEnumConstants();
    if (enumValues != null) {
      for (Object enumValue : enumValues) {
        if (Objects.equals(value, String.valueOf(enumValue))) {
          result = true;
          break;
        }
      }
    }
    return result;
  }
}