package com.github.xonixx.sample_app_1.service_1;

import lombok.RequiredArgsConstructor;

import java.math.MathContext;
import java.math.RoundingMode;

@RequiredArgsConstructor
public enum CalcOperation {
  add(2, args -> args.get(0).add(args.get(1))),
  subtract(2, args -> args.get(0).subtract(args.get(1))),
  multiply(2, args -> args.get(0).multiply(args.get(1))),
  divide(2, args -> args.get(0).divide(args.get(1), 10, RoundingMode.HALF_EVEN)),
  sqrt(1, args -> args.get(0).sqrt(MathContext.DECIMAL64));

  final int argsCount;
  final CalculateFunc calculateFunc;
}
