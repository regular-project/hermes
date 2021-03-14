package org.hermes.core.cache;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemoryCacheTest {

    @Test
    public void testIsElementInCache() {
        long cacheValidInterval = 5000L;
        long now = new Date().getTime();

        CacheableElementImpl cacheableElement = mock(CacheableElementImpl.class);
        cacheableElement.setId(1);

        MemoryCache memoryCache = new MemoryCache(cacheValidInterval);

        when(cacheableElement.getCreationDate()).thenReturn(now);
        assertFalse(memoryCache.isElementInCache(cacheableElement));
        assertTrue(memoryCache.isElementInCache(cacheableElement));

        when(cacheableElement.getCreationDate()).thenReturn(now + cacheValidInterval * 2);
        assertFalse(memoryCache.isElementInCache(cacheableElement));
    }
}