package org.hermes.jsonhandler.extraction;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.ExtractionResult;
import org.hermes.core.avro.HermesRecord;
import org.hermes.core.extraction.DataExtractor;

public class JsonDataExtractor implements DataExtractor {

    @Override
    public ExtractionResult extract(ConsumerRecord<String, HermesRecord> record) {
        System.out.println(record.value());
        return null;
    }
}
