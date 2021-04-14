package org.hermes.core.cache;

import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CacheUtilsTest {

    @Test
    public void testIsOldElement() {
        long cacheValidInterval = 5000L;
        long now = new Date().getTime();

        CacheableElementImpl cacheableElement = mock(CacheableElementImpl.class);
        cacheableElement.setId(1);

        assertFalse(CacheUtils.isOldElement(cacheableElement, now, cacheValidInterval));

        when(cacheableElement.getCreationDate()).thenReturn(now + cacheValidInterval * 2);
        assertTrue(CacheUtils.isOldElement(cacheableElement, now, cacheValidInterval));
    }

}