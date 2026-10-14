package com.example.customerorders.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI customerOrdersOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Customer Orders API")
                .version("v1")
                .description("REST API for managing customers and their orders."));
    }
}