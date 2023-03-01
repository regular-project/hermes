package org.hermes.core.extraction;

import org.apache.avro.specific.SpecificRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.springframework.stereotype.Component;

@Component
public interface DataExtractor {

    HermesEgressRecord extract(SpecificRecord record);
}
