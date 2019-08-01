package com.github.xonixx.sample_app_1.service_1;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Getter
public class ValidationException extends RuntimeException {

  /** in case if multiple errors present */
  private ValidationErrors validationErrors;

  public ValidationException(ValidationErrors validationErrors) {
    super(validationErrors.getMessage());
    if (!validationErrors.hasErrors()) {
      throw new IllegalArgumentException();
    }
    this.validationErrors = validationErrors;
  }
}
