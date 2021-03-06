package org.hermes.jsonhandler.cache;

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

        CacheableElementImpl cacheableElement = mock(CacheableElementImpl.class);
        MemoryCache memoryCache = new MemoryCache(cacheValidInterval);

        when(cacheableElement.getCreationDate()).thenReturn(new Date().getTime());
        assertFalse(memoryCache.isElementInCache(cacheableElement));
        assertTrue(memoryCache.isElementInCache(cacheableElement));

        when(cacheableElement.getCreationDate()).thenReturn(new Date().getTime() + cacheValidInterval * 2);
        assertFalse(memoryCache.isElementInCache(cacheableElement));
    }

    static class CacheableElementImpl extends CacheableElement {

        private final int id;

        CacheableElementImpl(int id) {
            this.id = id;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CacheableElementImpl)) return false;

            CacheableElementImpl that = (CacheableElementImpl) o;

            return id == that.id;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }
}