package com.meresti.bookstoreclient.config;

import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;

@RequiredArgsConstructor
public class CircuitBreakerCustomizer implements Customizer<Resilience4JCircuitBreakerFactory> {

    private static final String DEFAULT = "default";

    private final String id;

    private final TimeLimiterConfig timeLimiterConfig;

    private final io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig;

    @Override
    public void customize(final Resilience4JCircuitBreakerFactory factory) {
        if (DEFAULT.equals(id)) {
            factory.configureDefault(this::customizeDefault);
        } else {
            factory.configure(this::customizeOther, id);
        }
    }

    private Resilience4JConfigBuilder.Resilience4JCircuitBreakerConfiguration customizeDefault(final String id) {
        return new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig)
                .build();
    }

    private void customizeOther(final Resilience4JConfigBuilder builder) {
        builder.timeLimiterConfig(timeLimiterConfig)
                .circuitBreakerConfig(circuitBreakerConfig);
    }

}
