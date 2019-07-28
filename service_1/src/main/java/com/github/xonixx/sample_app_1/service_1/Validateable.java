package com.github.xonixx.sample_app_1.service_1;

public interface Validateable {

  /** @return validation errors */
  default ValidationErrors validate() {
    ValidationErrors validationErrors = new ValidationErrors();
    validate(validationErrors::add);
    return validationErrors;
  }

  /**
   * Generates all validation errors for this entity
   *
   * @param onValidationError callback to capture all errors
   */
  void validate(ValidationErrorHandler onValidationError);

  interface ValidationErrorHandler {
    void onValidationError(ValidationError validationError);
  }
}
