package org.hermes.core.cache;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CombinedCacheTest {

    @Test
    public void testIsElementInCache() {
        MemoryCache memoryCache = mock(MemoryCache.class);
        RedisCache redisCache = mock(RedisCache.class);

        CombinedCache CombinedCache = new CombinedCache(memoryCache, redisCache);

        CacheableElement cacheableElement = new CacheableElement();

        when(memoryCache.isElementInCache(cacheableElement)).thenReturn(false);
        when(redisCache.isElementInCache(cacheableElement)).thenReturn(false);
        assertFalse(CombinedCache.isElementInCombinedCache(cacheableElement));

        when(memoryCache.isElementInCache(cacheableElement)).thenReturn(true);
        when(redisCache.isElementInCache(cacheableElement)).thenReturn(false);
        assertTrue(CombinedCache.isElementInCombinedCache(cacheableElement));

        when(memoryCache.isElementInCache(cacheableElement)).thenReturn(true);
        when(redisCache.isElementInCache(cacheableElement)).thenReturn(true);
        assertTrue(CombinedCache.isElementInCombinedCache(cacheableElement));
    }
}
