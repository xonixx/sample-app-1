package com.github.xonixx.sample_app_1.service_2.calc_client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
@JsonDeserialize(builder = CalcResult.CalcResultBuilder.class)
public class CalcResult {
  private final BigDecimal result;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CalcResultBuilder {}
}
