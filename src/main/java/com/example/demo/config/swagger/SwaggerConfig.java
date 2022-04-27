package com.example.demo.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class SwaggerConfig {
    private final SwaggerConfigPops swaggerConfigPops;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title(swaggerConfigPops.getTitle())
                .description(swaggerConfigPops.getDescription())
                .version(swaggerConfigPops.getVersion());
    }
}
