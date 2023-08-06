package com.example.feignclientplayground;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SampleService {

  private final SampleFeignClient feignClient;

  public ServiceResponse doFeignCall(ServiceRequest requestBody) {
    return feignClient.routeSearch(requestBody);
  }
}
