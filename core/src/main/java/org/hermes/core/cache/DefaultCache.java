package org.hermes.core.cache;

public abstract class DefaultCache {

    Long cacheValidInterval;

    DefaultCache(Long cacheValidInterval) {
        this.cacheValidInterval = cacheValidInterval;
    }

    abstract boolean isElementInCache(CacheableElement element) throws InterruptedException;
}
