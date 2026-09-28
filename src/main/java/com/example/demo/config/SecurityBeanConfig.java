package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.SecureRandom;

@Configuration
public class SecurityBeanConfig {
    @Bean
    public SecureRandom secureRandom() {
        return new SecureRandom();
    }
}
