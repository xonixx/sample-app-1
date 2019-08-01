package com.github.xonixx.sample_app_1.service_2;

import com.github.xonixx.sample_app_1.service_2.calc_client.CalcCallerService;
import com.github.xonixx.sample_app_1.service_2.calc_client.CalcRequest;
import com.github.xonixx.sample_app_1.service_2.calc_client.CalcResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/calc")
public class CalcController {

  private final CalcCallerService calcCallerService;

  @PostMapping
  public CalcResult calculate(@Valid @RequestBody CalcRequest calcRequest) {
    return calcCallerService.calc(calcRequest);
  }
}
