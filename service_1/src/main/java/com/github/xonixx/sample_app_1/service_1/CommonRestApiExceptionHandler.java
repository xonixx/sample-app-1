package com.github.xonixx.sample_app_1.service_1;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestControllerAdvice(annotations = RestController.class)
public class CommonRestApiExceptionHandler {

  private final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleException(HttpServletRequest request, Throwable ex) {
    logger.error(ex.getMessage(), ex);
    HttpStatus status = getStatus(request);
    String message = ex.getMessage();
    ResponseStatus responseStatusAnn = ex.getClass().getAnnotation(ResponseStatus.class);
    if (responseStatusAnn == null && ex.getClass().getSuperclass() != null)
      responseStatusAnn = ex.getClass().getSuperclass().getAnnotation(ResponseStatus.class);
    if (responseStatusAnn != null) {
      status = responseStatusAnn.value();
    }

    List<ValidationError> validationErrors = null;

    if (ex instanceof ValidationException) {
      ValidationErrors e = ((ValidationException) ex).getValidationErrors();
      if (e != null) {
        validationErrors = e.getErrors();
      }
    }

    ErrorResponse errorResponse =
        new ErrorResponse(status.value(), message, ex.getClass().getSimpleName(), validationErrors);

    return new ResponseEntity<>(errorResponse, status);
  }

  private HttpStatus getStatus(HttpServletRequest request) {
    Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
    if (statusCode == null) {
      return HttpStatus.INTERNAL_SERVER_ERROR;
    }
    return HttpStatus.valueOf(statusCode);
  }

  @AllArgsConstructor
  @Getter
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  private static class ErrorResponse {
    private final Integer httpCode;
    private final String message;
    private final String exception;
    private final List<ValidationError> validationErrors;
  }
}
