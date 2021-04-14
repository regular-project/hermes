package org.hermes.core.cache;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedisCacheTest {

    @Test
    public void testIsElementInCache() {
        long cacheValidInterval = 5000L;
        long now = new Date().getTime();

        CacheableElementImpl cacheableElement = mock(CacheableElementImpl.class);
        cacheableElement.setId(1);

        String cacheableElementHashcode =  String.valueOf(cacheableElement.hashCode());
        when(cacheableElement.getCreationDate()).thenReturn(now);

        Jedis jedis = mock(Jedis.class);
        Transaction transaction = mock(Transaction.class);

        RedisCache redisCache = new RedisCache(cacheValidInterval, jedis);

        when(jedis.get(cacheableElementHashcode)).thenReturn(null);
        when(jedis.multi()).thenReturn(transaction);
        assertFalse(redisCache.isElementInCache(cacheableElement));

        when(jedis.get(cacheableElementHashcode)).thenReturn(String.valueOf(now));
        when(jedis.multi()).thenReturn(transaction);
        assertTrue(redisCache.isElementInCache(cacheableElement));

        when(cacheableElement.getCreationDate()).thenReturn(now + cacheValidInterval * 2);
        when(jedis.multi()).thenReturn(transaction);
        assertFalse(redisCache.isElementInCache(cacheableElement));

        when(transaction.exec()).thenReturn(null);
        assertTrue(redisCache.isElementInCache(cacheableElement));
    }
}