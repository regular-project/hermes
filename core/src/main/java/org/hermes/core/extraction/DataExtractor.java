package org.hermes.core.extraction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;

public interface DataExtractor {

    HermesEgressRecord extract(ConsumerRecord<String, HermesIngressRecord> records);
}
