package org.hermes.core.cache;

public abstract class DefaultCache {

    private final Long cacheValidInterval;

    DefaultCache(Long cacheValidInterval) {
        this.cacheValidInterval = cacheValidInterval;
    }

    public abstract boolean isElementInCache(CacheableElement element);

    Long getCacheValidInterval() {
        return cacheValidInterval;
    }
}
