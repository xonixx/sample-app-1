package com.github.xonixx.sample_app_1.service_1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/infra")
public class InfraController {
  @ResponseBody
  @GetMapping("/ping")
  public String ping() {
    return "OK";
  }
}
