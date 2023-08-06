package com.example.feignclientplayground;

import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorHandler {

  @ExceptionHandler(FeignException.class)
  public ResponseEntity<ErrorResponse> handleRequestError(FeignException ex) {

    return ResponseEntity.status(ex.status())
      .body(new ErrorResponse("Something went wrong"));
  }
}
