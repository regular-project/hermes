package org.hermes.core.cache;

import java.io.Serializable;
import java.util.Date;

public class CacheableElement implements Serializable {

    private final long creationDate = new Date().getTime();

    public long getCreationDate() {
        return creationDate;
    }
}
