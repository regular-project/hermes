package org.hermes.core.cache;

import redis.clients.jedis.Jedis;

public final class DefaultCacheFactory {

    private DefaultCacheFactory() { }

    public static DefaultCache getRedisBasedDefaultCache(Long cacheValidInterval, Jedis jedis) {
        return new RedisCache(cacheValidInterval, jedis);
    }
}
