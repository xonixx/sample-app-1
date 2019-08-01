package com.github.xonixx.sample_app_1.service_1;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CalcControllerTests extends ApiTestsBase {

  @Test
  public void testAdd() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/calc")
                .content("{\"operation\": \"add\", \"arguments\": [5.5, 2]}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is(7.5)));
  }

  @Test
  public void testSqrt() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/calc")
                .content("{\"operation\": \"sqrt\", \"arguments\": [25]}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is(5)));
  }

  @Test
  public void testMissingOperation() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/calc")
                .content("{\"arguments\": [25]}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("operation is required")));
  }

  @Test
  public void testWrongArgumentsCount() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/calc")
                .content("{\"operation\": \"add\", \"arguments\": [1, 2, 3, 4]}")
                .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("add requires 2 argument(s)")));
  }
}
