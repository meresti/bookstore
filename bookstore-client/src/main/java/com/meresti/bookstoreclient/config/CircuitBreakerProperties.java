package com.meresti.bookstoreclient.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookstore.circuitbreaker.configuration.circuitbreaker")
class CircuitBreakerProperties extends CircuitBreakerConfigurationProperties {
}
