package org.hermes.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

class MemoryCache extends DefaultCache {

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Map<Object, Long> cache = new HashMap<>();

    MemoryCache(Long cacheValidInterval) {
        super(cacheValidInterval);
    }

    @Override
    boolean isElementInCache(CacheableElement element) {
        boolean result = false;

        lock.lock();

        Long elementTime = cache.get(element);

        boolean isOldElement = true;
        if (elementTime != null) {
            isOldElement = CacheUtils.isOldElement(element, elementTime, super.getCacheValidInterval());
        }

        if (isOldElement) {
            cache.put(element, element.getCreationDate());
        } else {
            result = true;
        }

        lock.unlock();

        return result;
    }
}
