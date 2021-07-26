package org.hermes.core.cache;

import redis.clients.jedis.Jedis;

public class DefaultCacheFactory {

    public DefaultCacheFactory() { }

    public static DefaultCache getRedisBasedDefaultCache(Long cacheValidInterval, Jedis jedis) {
        return new RedisCache(cacheValidInterval, jedis);
    }
}
