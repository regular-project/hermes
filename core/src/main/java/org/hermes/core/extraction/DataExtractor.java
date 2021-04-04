package org.hermes.core.extraction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.ExtractionResult;
import org.hermes.core.avro.HermesRecord;

public interface DataExtractor {

    ExtractionResult extract(ConsumerRecord<String, HermesRecord> records);
}
