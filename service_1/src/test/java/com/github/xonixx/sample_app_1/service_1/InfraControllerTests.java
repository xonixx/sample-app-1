package com.github.xonixx.sample_app_1.service_1;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class InfraControllerTests extends ApiTestsBase {

  @Test
  public void testPing() throws Exception {
    mockMvc
        .perform(get("/infra/ping"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("OK"));
  }
}
