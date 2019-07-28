package com.github.xonixx.sample_app_1.service_1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;

import static com.github.xonixx.sample_app_1.service_1.ValidationError.validationError;

@Getter
@Builder
@JsonDeserialize(builder = CalcRequest.CalcRequestBuilder.class)
public class CalcRequest implements Validateable {
  private final CalcOperation operation;
  private final List<BigDecimal> arguments;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CalcRequestBuilder {}

  @Override
  public void validate(ValidationErrorHandler onValidationError) {
    if (operation == null) {
      onValidationError.onValidationError(validationError("operation is required"));
    } else {
      if (!argsSizeIs(operation.argsCount)) {
        onValidationError.onValidationError(
            validationError(operation + " requires " + operation.argsCount + " arguments"));
      }
    }
  }

  private boolean argsSizeIs(int n) {
    return !CollectionUtils.isEmpty(arguments) && arguments.size() == n;
  }
}
