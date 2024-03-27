package com.ssafy.matchup.auth.service.sub;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class JwtTokenRedisService {

    @Value("${jwt.period.refresh-token}")
    private long periodRefreshToken;

    private final ValueOperations<String, String> redisOperations;

    public JwtTokenRedisService(StringRedisTemplate stringRedisTemplate) {
        this.redisOperations = stringRedisTemplate.opsForValue();
    }

    public void save(String id, String refreshToken) {
        deleteById(id);
        redisOperations.set(id, refreshToken, periodRefreshToken, TimeUnit.MILLISECONDS);
    }

    public String findById(String id) {
        return redisOperations.get(id);
    }

    public void deleteById(String id) {
        if (findById(id) == null) return;
        redisOperations.getAndDelete(id);
    }
}
