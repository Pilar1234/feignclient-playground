package com.example.feignclientplayground;

import feign.Client;
import feign.okhttp.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

  @Bean
  Client client() {
    return new OkHttpClient();
  }
}
