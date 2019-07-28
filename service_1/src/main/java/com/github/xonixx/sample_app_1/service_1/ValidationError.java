package com.github.xonixx.sample_app_1.service_1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ValidationError {
  private final String message;

  public static ValidationError validationError(String message) {
    return new ValidationError(message);
  }

  @Override
  public String toString() {
    return message;
  }
}
