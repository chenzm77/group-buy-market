package com.github.chenzm77.infrastructure.adapter.redis;

import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisService {

    @Resource
    private RedissonClient redissonClient;

    public <T> T getValue(String key) {
        RBucket<T> bucket = redissonClient.getBucket(key);
        return bucket.get();
    }

    public <T> void setValue(String key, T value) {
        redissonClient.getBucket(key).set(value);
    }

    public <T> void setValue(String key, T value, long ttlSeconds) {
        redissonClient.getBucket(key).set(value, ttlSeconds, TimeUnit.SECONDS);
    }

    public boolean delete(String key) {
        return redissonClient.getBucket(key).delete();
    }

    public boolean exists(String key) {
        return redissonClient.getBucket(key).isExists();
    }
}