package org.hermes.core.cache;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;

class RedisCache extends DefaultCache {

    private final Jedis jedis;

    RedisCache(Long cacheValidInterval, Jedis jedis) {
        super(cacheValidInterval);
        this.jedis = jedis;
    }

    @Override
    boolean isElementInCache(CacheableElement element) {
        boolean result = false;

        String elementHash = String.valueOf(element.hashCode());

        jedis.watch(elementHash);

        String elementTime = jedis.get(elementHash);

        boolean isOldElement = true;
        if (elementTime != null) {
            isOldElement = CacheUtils.isOldElement(
                    element,
                    Long.valueOf(elementTime),
                    super.getCacheValidInterval()
            );
        }

        if (isOldElement) {
            Transaction transaction = jedis.multi();
            transaction.set(elementHash, elementTime);
            List<Object> res = transaction.exec();

            if (res == null) {
                result = true;
            }
        } else {
            result = true;
        }

        return result;
    }
}
