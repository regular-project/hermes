package org.hermes.core.extraction;

import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.cache.CacheableElement;

import java.util.Objects;

public class HermesEgressRecordCacheable extends CacheableElement {

    private final HermesEgressRecord hermesEgressRecord;

    public HermesEgressRecordCacheable(HermesEgressRecord hermesEgressRecord) {
        this.hermesEgressRecord = hermesEgressRecord;
    }

    public HermesEgressRecord getHermesEgressRecord() {
        return hermesEgressRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HermesEgressRecordCacheable)) return false;
        HermesEgressRecordCacheable that = (HermesEgressRecordCacheable) o;
        return Objects.equals(hermesEgressRecord, that.hermesEgressRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hermesEgressRecord);
    }
}

