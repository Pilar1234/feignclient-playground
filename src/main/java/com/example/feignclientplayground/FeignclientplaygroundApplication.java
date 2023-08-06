package com.example.feignclientplayground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class FeignclientplaygroundApplication {

  public static void main(String[] args) {
    SpringApplication.run(FeignclientplaygroundApplication.class, args);
  }

}
