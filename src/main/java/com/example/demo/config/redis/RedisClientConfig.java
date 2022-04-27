package com.example.demo.config.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.ReadFrom;
import io.lettuce.core.SocketOptions;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import io.lettuce.core.resource.ClientResources;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@Slf4j
@Configuration
@EnableRedisRepositories
@RequiredArgsConstructor
public class RedisClientConfig {

    private final RedisConfigProps redisConfigProps;

    @Bean
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public RedisConnectionFactory redisConnectionFactory() {

        RedisConfiguration redisConfiguration;
        ClientOptions clientOptions;

        SocketOptions socketOptions = SocketOptions.builder()
                .connectTimeout(redisConfigProps.getSocketTimeoutMillis())
                .build();

        if (redisConfigProps.useCluster()) {
            RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisConfigProps.getNodes());
            redisClusterConfiguration.setMaxRedirects(redisConfigProps.getMaxRedirect());

            redisConfiguration = redisClusterConfiguration;

            ClusterTopologyRefreshOptions topologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
                    .refreshPeriod(Duration.ofMillis(redisConfigProps.getRefreshPeriodMillis()))
                    .enablePeriodicRefresh()
                    .enableAllAdaptiveRefreshTriggers()
                    .build();

            clientOptions = ClusterClientOptions.builder()
                    .autoReconnect(true)
                    .topologyRefreshOptions(topologyRefreshOptions)
                    .socketOptions(socketOptions)
                    .build();
        }
        else {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisConfigProps.getHost(), redisConfigProps.getPort());
            redisStandaloneConfiguration.setPassword(RedisPassword.of(redisConfigProps.getPassword()));
            redisStandaloneConfiguration.setDatabase(redisConfigProps.getDatabase());

            redisConfiguration = redisStandaloneConfiguration;

            clientOptions = ClientOptions.builder()
                    .autoReconnect(true)
                    .socketOptions(socketOptions)
                    .build();
        }

        ClientResources clientResources = ClientResources.builder()
                .build();

        LettuceClientConfiguration clientConfiguration = LettuceClientConfiguration.builder()
                .clientOptions(clientOptions)
                .clientResources(clientResources)
                .readFrom(ReadFrom.REPLICA_PREFERRED)
                .commandTimeout(redisConfigProps.getCommandTimeoutMillis())
                .build();

        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisConfiguration, clientConfiguration);

        if (redisConfiguration instanceof RedisClusterConfiguration) {
            log.info("REDIS Connected : {}", redisConfigProps.getNodes());
        }
        else {
            log.info("REDIS Connected : {}:{}/{}", redisConfigProps.getHost(), redisConfigProps.getPort(), redisConfigProps.getDatabase());
        }

        return lettuceConnectionFactory;
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());

        return redisTemplate;
    }
}
