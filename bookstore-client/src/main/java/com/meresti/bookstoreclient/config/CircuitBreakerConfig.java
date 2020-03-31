package com.meresti.bookstoreclient.config;

import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Configuration(proxyBeanMethods = false)
public class CircuitBreakerConfig {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "bookstore.circuit-breaker.configuration.enabled", havingValue = "false", matchIfMissing = true)
    public static class CodeBasedConfigurator {
        @Bean
        public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
            return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                    .circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults())
                    .build());
        }

        @Bean
        public Customizer<Resilience4JCircuitBreakerFactory> titlesCustomizer() {
            return factory -> factory.configure(builder -> builder
                            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
                            .circuitBreakerConfig(io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.ofDefaults()),
                    "titles");
        }

    }

    @RequiredArgsConstructor
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties(CircuitBreakerProperties.class)
    @ConditionalOnProperty(name = "bookstore.circuit-breaker.configuration.enabled", havingValue = "true")
    public static class PropertyBasedConfigurator {
        private final DefaultListableBeanFactory beanFactory;

        private final CircuitBreakerProperties circuitBreakerProperties;

        @PostConstruct
        public void registerBeanDefinitions() {
            circuitBreakerProperties.getConfigs().forEach((id, config) -> {
                processConfig(id, config, beanFactory, circuitBreakerProperties);
            });
        }

        private void processConfig(final String id,
                                   final CircuitBreakerConfigurationProperties.InstanceProperties config,
                                   final BeanDefinitionRegistry registry,
                                   final CircuitBreakerProperties circuitBreakerProperties) {
            final io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig =
                    circuitBreakerProperties.createCircuitBreakerConfig(config);
            final TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.ofDefaults();
            registerBean(id, circuitBreakerConfig, timeLimiterConfig, registry);
        }

        private void registerBean(final String id,
                                  final io.github.resilience4j.circuitbreaker.CircuitBreakerConfig circuitBreakerConfig,
                                  final TimeLimiterConfig timeLimiterConfig,
                                  final BeanDefinitionRegistry registry) {
            final BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(CircuitBreakerCustomizer.class);
            definition.addConstructorArgValue(id);
            definition.addConstructorArgValue(timeLimiterConfig);
            definition.addConstructorArgValue(circuitBreakerConfig);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);

            final AbstractBeanDefinition beanDefinition = definition.getBeanDefinition();
            registry.registerBeanDefinition(id + "Customizer", beanDefinition);
        }
    }

}
