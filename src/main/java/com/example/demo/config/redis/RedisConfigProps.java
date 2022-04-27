package com.example.demo.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfigProps {

    private boolean useCluser;
    private int maxRedirect;
    private int refreshPeriodMillis;
    private List<String> nodes;
    private String host;
    private int port;
    private String password;
    private int database;
    private Duration commandTimeoutMillis;
    private Duration socketTimeoutMillis;

    public boolean useCluster() {
        return this.useCluser;
    }
}
