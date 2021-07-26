package org.hermes.core.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.avro.HermesIngressRecord;
import org.hermes.core.extraction.HermesEgressRecordCacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class HermesConsumerService {

    private final HermesExtractorService hermesExtractorService;
    private final ApplicationEventPublisher publisher;
    private final ExecutorService executorService;

    public HermesConsumerService(HermesExtractorService hermesExtractorService, ApplicationEventPublisher publisher) {
        this.hermesExtractorService = hermesExtractorService;
        this.publisher = publisher;
        this.executorService = new ThreadPoolExecutor(0, 30, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    @KafkaListener(topics = "${hermes.kafka.consumer.topic}")
    public void consume(ConsumerRecord<String, HermesIngressRecord> record) {
        executorService.execute(() -> {
            HermesEgressRecord hermesEgressRecord = hermesExtractorService.extract(record);

            if (hermesEgressRecord != null) publisher.publishEvent(new HermesEgressRecordCacheable(hermesEgressRecord));
        });
    }
}
