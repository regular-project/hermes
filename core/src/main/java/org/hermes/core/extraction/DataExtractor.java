package org.hermes.core.extraction;

import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.springframework.stereotype.Component;

@Component
public interface DataExtractor {

    HermesEgressRecord extract(HermesIngressRecord record) throws Exception;
}
