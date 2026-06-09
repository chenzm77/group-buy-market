package com.github.chenzm77.goods.infrastructure.cache;

import com.github.chenzm77.goods.infrastructure.redis.RedisService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class CacheAsideHelper {

    private static final int MAX_RETRY = 5;
    private static final long LOCK_WAIT_MILLIS = 100L;
    private static final long EMPTY_TTL_SECONDS = 120L;
    private static final String NULL_CACHE_VALUE = "__NULL_CACHE_VALUE__";

    @Resource
    private RedisService redisService;

    @Resource
    private RedissonClient redissonClient;

    public <T> T query(String cacheKey, String lockKey, long ttlSeconds, Class<T> clazz, Supplier<T> dbLoader) {
        Object cached = redisService.getValue(cacheKey);
        if (NULL_CACHE_VALUE.equals(cached)) {
            return null;
        }
        if (clazz.isInstance(cached)) {
            return clazz.cast(cached);
        }
        for (int i = 0; i < MAX_RETRY; i++) {
            RLock lock = redissonClient.getLock(lockKey);
            boolean locked = false;
            try {
                locked = lock.tryLock(0, 1, TimeUnit.SECONDS);
                if (locked) {
                    Object doubleCheck = redisService.getValue(cacheKey);
                    if (NULL_CACHE_VALUE.equals(doubleCheck)) {
                        return null;
                    }
                    if (clazz.isInstance(doubleCheck)) {
                        return clazz.cast(doubleCheck);
                    }
                    T loaded = dbLoader.get();
                    if (loaded == null) {
                        redisService.setValue(cacheKey, NULL_CACHE_VALUE, EMPTY_TTL_SECONDS);
                        return null;
                    }
                    redisService.setValue(cacheKey, loaded, ttlSeconds);
                    return loaded;
                }
                TimeUnit.MILLISECONDS.sleep(LOCK_WAIT_MILLIS);
                Object retryCached = redisService.getValue(cacheKey);
                if (NULL_CACHE_VALUE.equals(retryCached)) {
                    return null;
                }
                if (clazz.isInstance(retryCached)) {
                    return clazz.cast(retryCached);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return dbLoader.get();
            } finally {
                if (locked && lock.isHeldByCurrentThread()) {
                    lock.unlock();
                }
            }
        }
        return dbLoader.get();
    }
}
