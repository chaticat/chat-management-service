package com.chaticat.chatmanagementservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebConfig {

    @Value("${elastic.search.service.url}")
    private String elasticSearchServiceUrl;

    @Bean
    WebClient elaticSearchWebClient(){
        return WebClient.create(elasticSearchServiceUrl);
    }
}
