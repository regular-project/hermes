package org.hermes.core.cache;

public class CacheableElementImpl extends CacheableElement {

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CacheableElementImpl)) return false;

        CacheableElementImpl that = (CacheableElementImpl) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
