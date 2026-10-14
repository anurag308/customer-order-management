package com.example.customerorders.integration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.http.HttpClient;
import java.time.Duration;
import java.util.Objects;

@Configuration
public class ExternalApiConfig {

    @Bean
        RestClient externalApiRestClient(@Value("${external-api.base-url}") String baseUrl) {
        HttpClient httpClient = Objects.requireNonNull(HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(3))
            .build());
        JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory(httpClient);
        requestFactory.setReadTimeout(Objects.requireNonNull(Duration.ofSeconds(5)));
        return RestClient.builder()
            .baseUrl(Objects.requireNonNull(baseUrl))
                .requestFactory(requestFactory)
                .build();
    }
}