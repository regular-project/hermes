package org.hermes.jsonhandler.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.DataExtractor;
import org.hermes.jsonhandler.cache.HermesEgressRecordCacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HermesConsumerService {

    private final DataExtractor dataExtractor;
    private final ApplicationEventPublisher publisher;

    public HermesConsumerService(DataExtractor dataExtractor, ApplicationEventPublisher publisher) {
        this.dataExtractor = dataExtractor;
        this.publisher = publisher;
    }

    @KafkaListener(topics = "${hermes.kafka.consumer.topic}")
    public void consume(ConsumerRecord<String, HermesIngressRecord> records) {
        HermesEgressRecord hermesEgressRecord = dataExtractor.extract(records);
        publisher.publishEvent(new HermesEgressRecordCacheable(hermesEgressRecord));
    }
}
