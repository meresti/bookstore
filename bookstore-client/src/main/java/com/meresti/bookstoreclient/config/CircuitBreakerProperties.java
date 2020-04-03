package com.meresti.bookstoreclient.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigurationProperties;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = "bookstore.circuit-breaker.configuration")
class CircuitBreakerProperties {

    @Getter
    @NestedConfigurationProperty
    private final CircuitBreakerConfigProperties circuitBreaker = new CircuitBreakerConfigProperties();

    @Getter
    @NestedConfigurationProperty
    private final TimeLimiterProperties timeLimiter = new TimeLimiterProperties();

    static class CircuitBreakerConfigProperties extends CircuitBreakerConfigurationProperties {
    }

    static class TimeLimiterProperties extends TimeLimiterConfigurationProperties {
    }

}
