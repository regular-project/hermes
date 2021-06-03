package org.hermes.core.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.HermesEgressRecordCacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class HermesConsumerService {

    private final HermesExtractorService hermesExtractorService;
    private final ApplicationEventPublisher publisher;

    public HermesConsumerService(HermesExtractorService hermesExtractorService, ApplicationEventPublisher publisher) {
        this.hermesExtractorService = hermesExtractorService;
        this.publisher = publisher;
    }

    @KafkaListener(topics = "${hermes.kafka.consumer.topic}")
    public void consume(ConsumerRecord<String, HermesIngressRecord> records) {
        HermesEgressRecord hermesEgressRecord = hermesExtractorService.extract(records);
        publisher.publishEvent(new HermesEgressRecordCacheable(hermesEgressRecord));
    }
}
