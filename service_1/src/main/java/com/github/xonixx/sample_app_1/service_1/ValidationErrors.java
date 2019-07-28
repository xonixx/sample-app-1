package com.github.xonixx.sample_app_1.service_1;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.github.xonixx.sample_app_1.service_1.ValidationError.validationError;

@Getter
public class ValidationErrors {
  public static final int MAX_ERRORS_TO_SHOW = 100;

  private List<ValidationError> errors = new ArrayList<>();

  public void add(ValidationError error) {
    if (error != null && errors.size() < MAX_ERRORS_TO_SHOW) {
      errors.add(error);
    }
  }

  public void add(String message) {
    add(validationError(message));
  }

  public boolean hasErrors() {
    return !errors.isEmpty();
  }

  public String getMessage() {
    return StringUtils.join(errors, ",\n");
  }
}
