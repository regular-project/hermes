package org.hermes.core.cache;

import java.util.Date;

public class CacheableElement {

    private final long creationDate = new Date().getTime();

    public long getCreationDate() {
        return creationDate;
    }
}
