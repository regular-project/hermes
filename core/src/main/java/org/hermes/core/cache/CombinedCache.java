package org.hermes.core.cache;

public final class CombinedCache {

    private final MemoryCache memoryCache;
    private final DefaultCache remoteCache;

    CombinedCache(MemoryCache memoryCache, DefaultCache remoteCache) {
        this.memoryCache = memoryCache;
        this.remoteCache = remoteCache;
    }

    public boolean isElementInCombinedCache(CacheableElement element) {
        return memoryCache.isElementInCache(element) || remoteCache.isElementInCache(element);
    }
}
