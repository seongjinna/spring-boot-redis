package com.example.demo.entity.redis;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@ToString
@Builder
@Getter
@Setter
@RedisHash(value = "market-info", timeToLive = 60*10L)
public class MarketInfoCacheDto {
    @Id
    String id;

    String market;

    String koreanName;

    String englishName;

    String marketWarning;
}
