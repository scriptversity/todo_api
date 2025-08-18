package com.leo.todo_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class AppConfig {
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    System.out.println("BCryptPasswordEncoder bean created");
    return new BCryptPasswordEncoder();
  }
}

