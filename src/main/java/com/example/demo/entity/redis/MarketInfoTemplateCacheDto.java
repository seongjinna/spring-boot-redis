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
public class MarketInfoTemplateCacheDto {
    String id;

    String market;

    String koreanName;

    String englishName;

    String marketWarning;
}
