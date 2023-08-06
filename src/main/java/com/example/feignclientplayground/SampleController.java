package com.example.feignclientplayground;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class SampleController {

  SampleService sampleService;

  @PostMapping("/v1/sample")
  public ResponseEntity<ServiceResponse> searchEvStations(
    @RequestBody ServiceRequest requestBody) {
    ServiceResponse placeServiceResponse = sampleService.doFeignCall(requestBody);
    return ResponseEntity.ok(placeServiceResponse);
  }

}
