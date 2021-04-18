package org.hermes.core.cache;

import java.util.Date;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CacheTimeUtilsTest {

    @Test
    public void testIsOldElement() {
        long cacheValidInterval = 5000L;
        long now = new Date().getTime();

        CacheableElementImpl cacheableElement = mock(CacheableElementImpl.class);
        cacheableElement.setId(1);

        assertFalse(CacheTimeUtils.isOldElement(cacheableElement, now, cacheValidInterval));

        when(cacheableElement.getCreationDate()).thenReturn(now + cacheValidInterval * 2);
        assertTrue(CacheTimeUtils.isOldElement(cacheableElement, now, cacheValidInterval));
    }

}