package org.hermes.core.util.logger;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.hermes.core.avro.HermesEgressRecord;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProducerLogger implements ProducerListener<String, HermesEgressRecord> {

    @Override
    public void onSuccess(ProducerRecord<String, HermesEgressRecord> record, RecordMetadata metadata) {
        log.info("Sent message to topic {} with offset=[{}]", metadata.topic(), metadata.offset());
    }

    @Override
    public void onError(ProducerRecord<String, HermesEgressRecord> record, RecordMetadata metadata, Exception e) {
        log.error("Unable to send message due to: {}", e.getMessage(), e);
    }
}
