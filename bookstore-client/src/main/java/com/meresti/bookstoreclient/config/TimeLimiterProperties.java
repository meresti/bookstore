package com.meresti.bookstoreclient.config;

import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bookstore.circuitbreaker.configuration.timelimiter")
class TimeLimiterProperties extends TimeLimiterConfigurationProperties {
}
