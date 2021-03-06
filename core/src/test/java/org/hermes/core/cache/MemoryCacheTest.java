package org.hermes.core.cache;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Objects;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.*;

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

        public CacheableElementImpl(int id) {
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
            return Objects.hash(id);
        }
    }
}