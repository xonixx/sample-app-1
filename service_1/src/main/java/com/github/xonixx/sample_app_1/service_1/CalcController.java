package com.github.xonixx.sample_app_1.service_1;

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

  private final CalcService calcService;

  @PostMapping
  public CalcResult calculate(@Valid @RequestBody CalcRequest calcRequest) {
    ValidationErrors validationErrors = calcRequest.validate();
    if (validationErrors.hasErrors()) {
      throw new ValidationException(validationErrors);
    }
    return calcService.calculate(calcRequest);
  }
}
