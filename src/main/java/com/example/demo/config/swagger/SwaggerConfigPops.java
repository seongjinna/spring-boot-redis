package com.example.demo.config.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "springdoc.swagger-ui")
public class SwaggerConfigPops {
    private String title;
    private String description;
    private String version;
}
