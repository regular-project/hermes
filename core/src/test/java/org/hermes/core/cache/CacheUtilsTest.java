package org.hermes.core.cache;


import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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