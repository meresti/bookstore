package com.meresti.bookstoreclient.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.common.CompositeCustomizer;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigurationProperties;
import io.github.resilience4j.common.timelimiter.configuration.TimeLimiterConfigurationProperties;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.Data;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Configuration(proxyBeanMethods = false)
public class CircuitBreakerAutoConfig {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnProperty(name = "bookstore.circuit-breaker.configuration.enabled", havingValue = "false", matchIfMissing = true)
    public static class CodeBasedConfigurator {
        @Bean
        public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
            return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                    .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                    .build());
        }

        @Bean
        public Customizer<Resilience4JCircuitBreakerFactory> titlesCustomizer() {
            return factory -> factory.configure(builder -> builder
                            .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(2)).build())
                            .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults()),
                    "titles");
        }

    }

    @RequiredArgsConstructor
    @Configuration(proxyBeanMethods = false)
    @EnableConfigurationProperties({CircuitBreakerProperties.class})
    @ConditionalOnProperty(name = "bookstore.circuit-breaker.configuration.enabled", havingValue = "true")
    public static class PropertyBasedConfigurator {
        private final DefaultListableBeanFactory beanFactory;

        private final CircuitBreakerProperties circuitBreakerProperties;

        @PostConstruct
        public void registerBeanDefinitions() {
            final Map<String, Configs> configMap = createConfigMap();
            configMap.forEach((id, configs) -> {
                registerBean(id, getCircuitBreakerConfig(configs), getTimeLimiterConfig(configs), beanFactory);
            });
        }

        private Map<String, Configs> createConfigMap() {
            final Map<String, Configs> configMap = new HashMap<>();
            collectConfigs(configMap, circuitBreakerProperties.getCircuitBreaker().getConfigs(), this::createCircuitBreakerConfig, Configs::setCircuitBreakerConfig);
            collectConfigs(configMap, circuitBreakerProperties.getCircuitBreaker().getInstances(), this::createCircuitBreakerConfig, Configs::setCircuitBreakerConfig);
            collectConfigs(configMap, circuitBreakerProperties.getTimeLimiter().getConfigs(), this::createTimeLimiterConfig, Configs::setTimeLimiterConfig);
            collectConfigs(configMap, circuitBreakerProperties.getTimeLimiter().getInstances(), this::createTimeLimiterConfig, Configs::setTimeLimiterConfig);
            return configMap;
        }

        private <PROP, CONF> void collectConfigs(final Map<String, Configs> configMap,
                                                 final Map<String, PROP> properties,
                                                 final BiFunction<String, PROP, CONF> creator,
                                                 final BiConsumer<Configs, CONF> setter) {
            properties.forEach((id, config) -> {
                        final Configs configs = configMap.computeIfAbsent(id, s -> new Configs());
                        final CONF conf = creator.apply(id, config);
                        setter.accept(configs, conf);
                    }
            );
        }

        private CircuitBreakerConfig createCircuitBreakerConfig(final String id,
                                                                final CircuitBreakerConfigurationProperties.InstanceProperties config) {
            return circuitBreakerProperties.getCircuitBreaker().createCircuitBreakerConfig(id, config, new CompositeCustomizer<>(null));
        }

        private TimeLimiterConfig createTimeLimiterConfig(final String id,
                                                          final TimeLimiterConfigurationProperties.InstanceProperties config) {
            return circuitBreakerProperties.getTimeLimiter().createTimeLimiterConfig(id, config, new CompositeCustomizer<>(null));
        }

        private CircuitBreakerConfig getCircuitBreakerConfig(final Configs configs) {
            return Optional.ofNullable(configs.getCircuitBreakerConfig()).orElse(CircuitBreakerConfig.ofDefaults());
        }

        private TimeLimiterConfig getTimeLimiterConfig(final Configs configs) {
            return Optional.ofNullable(configs.getTimeLimiterConfig()).orElse(TimeLimiterConfig.ofDefaults());
        }

        private void registerBean(final String id,
                                  final CircuitBreakerConfig circuitBreakerConfig,
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

    @Data
    private static class Configs {
        private CircuitBreakerConfig circuitBreakerConfig;
        private TimeLimiterConfig timeLimiterConfig;
    }

}
