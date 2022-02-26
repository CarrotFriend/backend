package com.carrotfriend.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> template;

    public void set(String key, Object o, long milliseconds){
        template.setValueSerializer(new Jackson2JsonRedisSerializer(o.getClass()));
        template.opsForValue().set(key, o, milliseconds, TimeUnit.MILLISECONDS);
    }

    public Object get(String key){
        return template.opsForValue().get(key);
    }

    public boolean delete(String key){
        return template.delete(key);
    }

    public boolean hasKey(String key){
        return template.hasKey(key);
    }
}
