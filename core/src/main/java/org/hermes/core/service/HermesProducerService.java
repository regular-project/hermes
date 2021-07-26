package org.hermes.core.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.cache.CombinedCache;
import org.hermes.core.cache.DefaultCache;
import org.hermes.core.extraction.HermesEgressRecordCacheable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Service
@Slf4j
public class HermesProducerService {

    @Value("${hermes.kafka.producer.topic}")
    private String topic;

    @Value("${hermes.kafka.producer.key}")
    private String producerKey;

    private final DefaultCache defaultCache;
    private final KafkaTemplate<String, HermesEgressRecord> kafkaTemplate;

    public HermesProducerService(DefaultCache defaultCache, KafkaTemplate<String, HermesEgressRecord> kafkaTemplate) {
        this.defaultCache = defaultCache;
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void handlePublishHermesEgressRecordEvent(HermesEgressRecordCacheable hermesEgressRecordCacheable) {
        if (!defaultCache.isElementInCache(hermesEgressRecordCacheable)) {
            ProducerRecord<String, HermesEgressRecord> producerRecord = new ProducerRecord<>(
                    topic,
                    producerKey,
                    hermesEgressRecordCacheable.getHermesEgressRecord()
            );

            kafkaTemplate.send(producerRecord);
        }
    }
}
