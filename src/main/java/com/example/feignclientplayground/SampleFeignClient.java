package com.example.feignclientplayground;

import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sample-service", url = "${sample-service-url}", configuration = FeignConfig.class)
@Retry(name = "sample-service")
public interface SampleFeignClient {

  @PostMapping("/v1/test")
  ServiceResponse routeSearch(@RequestBody ServiceRequest request);
}
