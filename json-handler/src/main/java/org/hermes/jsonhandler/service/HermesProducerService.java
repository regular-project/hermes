package org.hermes.jsonhandler.service;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.hermes.core.avro.HermesEgressRecord;
import org.hermes.core.cache.CombinedCache;
import org.hermes.core.extraction.HermesEgressRecordCacheable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HermesProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HermesProducerService.class);
    private final CombinedCache combinedCache;
    private final KafkaTemplate<String, HermesEgressRecord> kafkaTemplate;
    @Value("${hermes.kafka.producer.topic}")
    private String topic;
    @Value("${hermes.kafka.producer.key}")
    private String producerKey;

    public HermesProducerService(CombinedCache combinedCache, KafkaTemplate<String, HermesEgressRecord> kafkaTemplate) {
        this.combinedCache = combinedCache;
        this.kafkaTemplate = kafkaTemplate;
    }

    @EventListener
    public void handlePublishHermesEgressRecordEvent(HermesEgressRecordCacheable hermesEgressRecordCacheable) {
        if (!combinedCache.isElementInCombinedCache(hermesEgressRecordCacheable)) {
            ProducerRecord<String, HermesEgressRecord> producerRecord = new ProducerRecord<>(
                    topic,
                    producerKey,
                    hermesEgressRecordCacheable.getHermesEgressRecord()
            );
            kafkaTemplate.send(producerRecord);
        }
    }
}
