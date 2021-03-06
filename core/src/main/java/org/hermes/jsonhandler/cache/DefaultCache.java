package org.hermes.jsonhandler.cache;

public abstract class DefaultCache {

    private final Long cacheValidInterval;

    DefaultCache(Long cacheValidInterval) {
        this.cacheValidInterval = cacheValidInterval;
    }

    abstract boolean isElementInCache(CacheableElement element) throws InterruptedException;

    public Long getCacheValidInterval() {
        return cacheValidInterval;
    }
}
