package com.meresti.bookstoreclient.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "resilience4j.circuitbreaker")
public class CircuitBreakerProperties extends CircuitBreakerConfigurationProperties {
}
