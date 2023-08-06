package com.example.feignclientplayground;

import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;
import static org.springframework.http.HttpStatus.TOO_MANY_REQUESTS;

import feign.FeignException;
import java.util.function.Predicate;

public class RetryPredicate implements Predicate<FeignException> {
  @Override
  public boolean test(FeignException e) {
    if (e.status() == SERVICE_UNAVAILABLE.value()
      || e.status() == TOO_MANY_REQUESTS.value()
    ) {
      return true;
    }
    return false;
  }
}
