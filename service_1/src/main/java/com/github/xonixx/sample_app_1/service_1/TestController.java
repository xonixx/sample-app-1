package com.github.xonixx.sample_app_1.service_1;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
public class TestController {
  @ResponseBody
  @GetMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
  public String root() {
    return "<h1>Edison 2";
  }
}
