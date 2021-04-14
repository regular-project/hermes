package org.hermes.core.extraction;

import org.apache.kafka.clients.consumer.*;
import org.hermes.core.avro.*;

public interface DataExtractor {

    HermesEgressRecord extract(ConsumerRecord<String, HermesIngressRecord> records);
}
