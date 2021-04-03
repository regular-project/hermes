package org.hermes.core.cache;

final class CacheUtils {

    private CacheUtils() {
    }

    static boolean isOldElement(CacheableElement element, Long time, Long cacheValidInterval) {
        return element.getCreationDate() - time > cacheValidInterval;
    }
}
