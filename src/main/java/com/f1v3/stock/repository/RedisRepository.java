package com.f1v3.stock.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.Duration;

/**
 * {class name}.
 *
 * @author 정승조
 * @version 2024. 10. 06.
 */
@Repository
public class RedisRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Boolean lock(Long key) {
        String generatedKey = generateKey(key);
        return redisTemplate
                .opsForValue()
                .setIfAbsent(generatedKey, "lock", Duration.ofMillis(3_000));

    }

    public Boolean unlock(Long key) {
        String generatedKey = generateKey(key);
        return redisTemplate.delete(generatedKey);
    }

    private String generateKey(Long key) {
        return key.toString();
    }


}
