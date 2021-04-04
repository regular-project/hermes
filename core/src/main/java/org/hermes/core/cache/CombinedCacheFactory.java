package org.hermes.core.cache;

import redis.clients.jedis.Jedis;

public final class CombinedCacheFactory {

    private CombinedCacheFactory() {
    }

    public static CombinedCache getRedisBasedCombinedCache(Long cacheValidInterval, Jedis jedis) {
        MemoryCache memoryCache = new MemoryCache(cacheValidInterval);
        RedisCache redisCache = new RedisCache(cacheValidInterval, jedis);

        return new CombinedCache(memoryCache, redisCache);
    }
}
