package org.hermes.core.cache;

final class CacheTimeUtils {

    private CacheTimeUtils() {
    }

    static boolean isOldElement(CacheableElement element, Long time, Long cacheValidInterval) {
        return element.getCreationDate() - time > cacheValidInterval;
    }
}
