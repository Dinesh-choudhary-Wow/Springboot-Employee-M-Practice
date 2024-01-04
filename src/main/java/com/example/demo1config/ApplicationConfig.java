package com.example.demo1config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = "com.example.demo1.vo")
@ComponentScan(basePackages = "com.example.demo1")
public class ApplicationConfig {
    // Your configuration
}
