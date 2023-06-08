package com.chaticat.chatmanagementservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig {

    @Value("${elastic.search.service.url}")
    private String elasticSearchServiceUrl;

    private final ApiHostConfig apiHostConfig;

    @Bean
    WebClient elaticSearchWebClient() {
        return WebClient.create(elasticSearchServiceUrl);
    }

    @Bean
    ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(apiHostConfig.getAllowedCorsOrigins())
                        .allowedMethods(apiHostConfig.getAllowedCorsMethods());
            }
        };
    }
}
