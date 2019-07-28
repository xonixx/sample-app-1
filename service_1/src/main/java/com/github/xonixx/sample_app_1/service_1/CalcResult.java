package com.github.xonixx.sample_app_1.service_1;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class CalcResult {
  private final BigDecimal result;
}
