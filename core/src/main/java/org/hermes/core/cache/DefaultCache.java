package org.hermes.core.cache;

abstract class DefaultCache {

    private final Long cacheValidInterval;

    DefaultCache(Long cacheValidInterval) {
        this.cacheValidInterval = cacheValidInterval;
    }

    abstract boolean isElementInCache(CacheableElement element);

    Long getCacheValidInterval() {
        return cacheValidInterval;
    }
}
