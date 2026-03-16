package com.techie.order_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI productServiceAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("Product Service API")
                        .description("Product microservice APIs")
                        .version("1.0"));
    }
}