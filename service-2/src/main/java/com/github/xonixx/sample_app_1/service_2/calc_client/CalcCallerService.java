package com.github.xonixx.sample_app_1.service_2.calc_client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CalcCallerService {
  private final String calcServiceUrl;
  private final RestTemplate restTemplate = new RestTemplate();

  public CalcCallerService(@Value("${app.calcServiceUrl}") String calcServiceUrl) {
    this.calcServiceUrl = calcServiceUrl;
  }

  public CalcResult calc(CalcRequest calcRequest) {
    return restTemplate.postForObject(calcServiceUrl, calcRequest, CalcResult.class);
  }
}
