package com.github.xonixx.sample_app_1.service_2.calc_client;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@JsonDeserialize(builder = CalcRequest.CalcRequestBuilder.class)
public class CalcRequest {
  private final CalcOperation operation;
  private final List<BigDecimal> arguments;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CalcRequestBuilder {}
}
