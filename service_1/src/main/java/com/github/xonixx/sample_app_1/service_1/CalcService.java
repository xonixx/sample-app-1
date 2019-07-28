package com.github.xonixx.sample_app_1.service_1;

import org.springframework.stereotype.Service;

@Service
public class CalcService {
  public CalcResult calculate(CalcRequest calcRequest) {
    return CalcResult.builder()
        .result(calcRequest.getOperation().calculateFunc.calculate(calcRequest.getArguments()))
        .build();
  }
}
