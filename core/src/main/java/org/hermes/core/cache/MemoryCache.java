package org.hermes.core.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class MemoryCache extends DefaultCache {

    private final ReentrantLock lock = new ReentrantLock(true);
    private final Map<Object, Long> cache = new HashMap<>();

    public MemoryCache(Long cacheValidInterval) {
        super(cacheValidInterval);
    }

    @Override
    public boolean isElementInCache(CacheableElement element) {
        boolean result = false;

        lock.lock();

        Long elementTime = cache.get(element);

        if (elementTime == null || element.getCreationDate() - elementTime > cacheValidInterval) {
            cache.put(element, element.getCreationDate());
        } else {
            result = true;
        }

        lock.unlock();

        return result;
    }
}
