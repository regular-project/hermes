package org.hermes.core.service;

import org.apache.avro.specific.SpecificRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.extraction.HermesEgressRecordCacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class HermesConsumerService {

    private final HermesExtractorService hermesExtractorService;
    private final ApplicationEventPublisher publisher;
    private final ExecutorService executorService;

    public HermesConsumerService(HermesExtractorService hermesExtractorService, ApplicationEventPublisher publisher) {
        this.hermesExtractorService = hermesExtractorService;
        this.publisher = publisher;
        this.executorService = Executors.newFixedThreadPool(30);
    }

    @KafkaListener(topics = "${hermes.kafka.consumer.topic}")
    public void consume(ConsumerRecord<String, SpecificRecord> record) {
        executorService.execute(() -> {
            HermesEgressRecord hermesEgressRecord = hermesExtractorService.extract(record);

            if (hermesEgressRecord != null) publisher.publishEvent(new HermesEgressRecordCacheable(hermesEgressRecord));
        });
    }
}
