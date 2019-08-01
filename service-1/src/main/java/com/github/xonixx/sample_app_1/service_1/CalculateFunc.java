package com.github.xonixx.sample_app_1.service_1;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface CalculateFunc {
  BigDecimal calculate(List<BigDecimal> arguments);
}
