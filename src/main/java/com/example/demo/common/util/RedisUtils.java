package com.example.demo.common.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String, Object> redisTemplate;
    private Gson gson = new Gson();

    public void reloadRedisConfigProps(Exception e) {
        redisTemplate.discard();
    }

    public void deleteRedisData(String key) {
        redisTemplate.delete(key);
    }

    public <T> void setRedisData(String key, T valueObject, int ttl) {
        String json = gson.toJson(valueObject, new TypeToken<T>(){}.getType());
        redisTemplate.opsForValue().set(key, json, Duration.ofSeconds(ttl));
    }

    public void setRedisData(String key, String value, int ttl) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttl));
    }

    public <T> boolean setRedisDataIfAbsent(String key, T valueObject, int ttl) {
        String json = gson.toJson(valueObject, new TypeToken<T>(){}.getType());
        return redisTemplate.opsForValue().setIfAbsent(key, json, Duration.ofSeconds(ttl));
    }

    public boolean setRedisDataIfAbsent(String key, String value, int ttl) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, Duration.ofSeconds(ttl));
    }

    public Object getRedisData(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Set<String> getRedisKeys(String prefix) {
        return redisTemplate.keys(prefix + "*");
    }

    public List<Object> getMultiRedisData (Set<String> keys) {
        return redisTemplate.opsForValue().multiGet(keys);
    }
}
